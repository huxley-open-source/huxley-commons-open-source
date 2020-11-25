package com.thehuxley.data;

import java.util.ArrayList;
import java.util.List;

import com.thehuxley.data.dao.database.LanguageDao;
import com.thehuxley.data.dao.database.ProblemDao;
import com.thehuxley.data.dao.database.QuestionnaireDao;
import com.thehuxley.data.dao.database.QuestionnaireShiroUserDao;
import com.thehuxley.data.dao.database.SubmissionDao;
import com.thehuxley.data.dao.database.UserDao;
import com.thehuxley.data.dao.database.UserProblemDao;
import com.thehuxley.data.dao.database.mysql.LanguageDaoMySQL;
import com.thehuxley.data.dao.database.mysql.ProblemDaoMySQL;
import com.thehuxley.data.dao.database.mysql.QuestionnaireDaoMySQL;
import com.thehuxley.data.dao.database.mysql.QuestionnaireShiroUserDaoMySQL;
import com.thehuxley.data.dao.database.mysql.SubmissionDaoMySQL;
import com.thehuxley.data.dao.database.mysql.UserDaoMySQL;
import com.thehuxley.data.dao.database.mysql.UserProblemDaoMySQL;
import com.thehuxley.data.model.database.Language;
import com.thehuxley.data.model.database.OracleSubmissions;
import com.thehuxley.data.model.database.Problem;
import com.thehuxley.data.model.database.Questionnaire;
import com.thehuxley.data.model.database.QuestionnaireShiroUser;
import com.thehuxley.data.model.database.QuestionnaireStatistics;
import com.thehuxley.data.model.database.Submission;
import com.thehuxley.data.model.database.User;
import com.thehuxley.memcached.CacheManager;

public final class DataBaseManager {

	private DataBaseManager() {
	}

	/**
	 * Retorna as submissões mais antigas que estão com o status de WAITING. A
	 * quantidade máxima de submissões é dada pelo valor do arquivo de
	 * properties cujo chave é: waiting_queue.size
	 * 
	 * @return
	 */
	// public static List<Submission> getTopWaitingSubmission() {
	// SubmissionDao dao = new SubmissionDaoMySQL();
	// return dao.selectTopWaiting();
	// }

	// public static void updateStatusForEvaluating(Submission submission) {
	// SubmissionDao dao = new SubmissionDaoMySQL();
	// dao.updateStatusForEvaluating(submission);
	// }

	public static Submission getSubmissionById(long id) {
		SubmissionDao dao = new SubmissionDaoMySQL();
		return dao.getSubmissionById(id);
	}

	public static void updateSubmission(Submission submission) {
		SubmissionDao dao = new SubmissionDaoMySQL();

		dao.updateSubmission(submission);
        //Update Memcached
        CacheManager.UpdateProblem(submission.getProblemId());

	}

	/**
	 * 
	 * 
	 * @param submission
	 * @return a lista de questionários que devem ser atualizados por causa
	 *         dessa submissão
	 */
	public static List<Long>  triggerAfterSubmissionEvaluation(Submission submission) {
		Problem problem = submission.getProblem();
        if (problem==null){
            throw new IllegalArgumentException("Problem cannot be null");
        }
        if (problem.getId()==0){
            throw new IllegalArgumentException("A problem with id 0 is not valid. It must be greater than 1");
        }
        if (submission.getId()==0){
            throw new IllegalArgumentException("Submission must be persistent, an id=0 is not allowed");
        }

		// Atualiza a tabela de cache da informacao se o usuario ja acertou um
		// determinado problema
		updateUserProblem(submission);

		// atualiza as informacoes do perfil do usuario (num. de problemas
		// tentados, numero de problemas corretos)
		updateProfile(submission.getUserId());

		// atualiza no perfil a quantidade de submissões do usuario
		// updateProfileSubmissionCount(submission.getUserId()); //nao precisa
		// mais, atualizei tbm no updateProfile

		if (submission.isEvaluationCorrect()) {
			// Só precisa atualizar se o tempo for realmente menor
            double currentFastestSubmission = problem.getFastestSubmissionTime();
            if (currentFastestSubmission<=0) currentFastestSubmission = Double.MAX_VALUE;
			if (submission.getTime() < currentFastestSubmission) {
				updateFastSubmissionProblem(submission);
			}
		}

		QuestionnaireDao questDao = new QuestionnaireDaoMySQL();

		// recupera todos os questionários abertos do usuário que fez a
		// submissão
		ArrayList<Long> questIdList = questDao
				.getQuestBySub(submission.getId());

		if (questIdList != null) {
			for (Long questId : questIdList) {
				// para cada questionário, recupera os problemas que ele acertou
				ArrayList<Long> problemIdList = questDao.getProblemCorrect(
						questId, submission.getId());
				/*
				 * Caso o aluno não tenha acertado algum problema do
				 * questionário problemIdList será uma lista vazia. Neste caso a
				 * nota do aluno será 0
				 */

				if (problemIdList != null) {
					String problems = problemIdList.toString();
					problems = problems.replace('[', '(');
					problems = problems.replace(']', ')');
					// atualiza a nota do aluno
					questDao.updateScore(problems, submission.getId(), questId);
				} else {
					// atualiza nota
					questDao.updateScoreWithEmptyProbList(submission.getId(),
							questId);
				}

			}
		}
		return questIdList;
	}

	public static boolean updateQuestionnaireStatistics(long questId) {

		QuestionnaireDao dao = new QuestionnaireDaoMySQL();

		// Pegar a lista de grupos em que esse questionário foi aplicado
		ArrayList<Long> groupIdList = dao.getGroupByQuest(questId);

        if (groupIdList==null){
            return true;
        }

		// Pegar as questoes do questionario
		// Verificar de quais questoes estao corretas
		// Atualizar as notas
		for (Long groupId : groupIdList) {
			double userInGroup = dao.getUserCountByGroup(groupId);
			double userTryed = dao.getUserTryes(questId, groupId);
			double greaterSeven = 0;
			double scoreTotal = 0;
			double lessSeven = 0;
			double scoreAverage = 0;
			double localAverageNote = 0;
			ArrayList<Double> scoreList = null;
			double variance = 0;
			double standardDeviation = 0;
			double tryPercentage = 0;

			if (userTryed != 0) {
				assert (userInGroup > 0);
				greaterSeven = dao.getGreaterSeven(questId, groupId);
				scoreTotal = dao.getScoreSum(questId, groupId);
				lessSeven = userTryed - greaterSeven;
				scoreAverage = scoreTotal / userTryed;
				localAverageNote = scoreTotal / userTryed;
				scoreList = dao.getGroupScore(questId, groupId);
                if (scoreList!=null) {
                    for (Double score : scoreList) {
                        variance += Math.pow(localAverageNote - score, 2);
                    }
                    variance = variance / userTryed;
                    standardDeviation = Math.sqrt(variance);
                }
				tryPercentage = userTryed / userInGroup;
			}

			QuestionnaireStatistics statistics = new QuestionnaireStatistics();
			statistics.setAverageNote(scoreAverage);
			statistics.setGreaterThenEqualsSeven(greaterSeven);
			statistics.setGroupId(groupId);
			statistics.setLessSeven(lessSeven);
			statistics.setQuestionnaireId(questId);
			statistics.setStandartDeviaton(standardDeviation);
			statistics.setTryPercentage(tryPercentage);
			statistics.setVersion(1);
			Long statisticsId = dao.getStatisticsId(questId, groupId);
			if (statisticsId != 0) {
				statistics.setId(statisticsId);
				dao.updateStatistics(statistics);
			} else {
				dao.saveStatistics(statistics);
			}

		}
		return true;

	}
	
	public static boolean triggerQuestionnaireWasUpdated(long questId){
		QuestionnaireDao quest = new QuestionnaireDaoMySQL();

		// Pegar a lista de grupos do usuario
		ArrayList<Long> questUserIdList = quest.getQuestUserList(questId);

		// Pegar as questoes do questionario
		// Verificar de quais questoes estao corretas
		// Atualizar as notas
        if (questUserIdList!=null) {
            QuestionnaireShiroUserDao questUser = new QuestionnaireShiroUserDaoMySQL();
            for (Long questUserId : questUserIdList) {
                Double score = quest.getScore(questUserId);
                questUser.updateScore(questUserId, score);

            }
        }
		return updateQuestionnaireStatistics(questId);
	}

	public static void updateProfile(long userId) {
		UserDaoMySQL dao = new UserDaoMySQL();
		dao.updateProfile(userId);
	}

	public static Problem getProblemById(long id) {
		ProblemDao dao = new ProblemDaoMySQL();
		return dao.getProblemById(id);
	}

	public static Language getLanguageById(long id) {
		LanguageDao dao = new LanguageDaoMySQL();
		return dao.getLanguageById(id);
	}

	public static void createProblem(Problem problem) {
		ProblemDao dao = new ProblemDaoMySQL();
		dao.createProblem(problem);
	}

	public static void updateProblem(Problem problem) {
		ProblemDao dao = new ProblemDaoMySQL();
		dao.updateProblem(problem);
	}

	public static void updateFastSubmissionProblem(Submission submission) {
		ProblemDao dao = new ProblemDaoMySQL();
		dao.updateFastestSub(submission);
	}

	// public static void updateFastSubmissionProblem(Long id) {
	// ProblemDao dao = new ProblemDaoMySQL();
	// dao.updateFastestSub(id);
	// }

	public static void deleteProblem(long id) {
		ProblemDao dao = new ProblemDaoMySQL();
		dao.deleteProblemById(id);
	}

	public static Questionnaire getQuestionnaireById(long id) {
		QuestionnaireDao dao = new QuestionnaireDaoMySQL();
		return dao.getQuestionnaireById(id);
	}

	public static void createQuestionnaire(Questionnaire questionnaire) {
		QuestionnaireDao dao = new QuestionnaireDaoMySQL();
		dao.createQuestionnaire(questionnaire);
	}

	public static void updateQuestionnaire(Questionnaire questionnaire) {
		QuestionnaireDao dao = new QuestionnaireDaoMySQL();
		dao.updateQuestionnaire(questionnaire);
	}

	public static void deleteQuestionnaire(long id) {
		QuestionnaireDao dao = new QuestionnaireDaoMySQL();
		dao.deleteQuestionnaireById(id);
	}

	public static User getUserById(long id) {
		UserDao dao = new UserDaoMySQL();
		return dao.getUserById(id);
	}

	/**
	 * Essa função corrige os questionários a partir de um de submissão
	 * 
	 * @param submissionId
	 *            id da submissão
	 */
	public static void correctQuestionnaires(long submissionId) {
		QuestionnaireShiroUserDao dao = new QuestionnaireShiroUserDaoMySQL();
		dao.correctQuestionnairesBySubmissionId(submissionId);

	}

	public static QuestionnaireShiroUser getQuestionnaireShiroUserById(long id) {
		QuestionnaireShiroUserDao dao = new QuestionnaireShiroUserDaoMySQL();
		return dao.getQuestionnaireShiroUserById(id);
	}

	public static void updateUserProblem(Submission submission) {
		UserProblemDao userProblem = new UserProblemDaoMySQL();
		userProblem.updateUserProblem(submission);
	}

    public static OracleSubmissions getOracleSubmissions(long problemId){
        SubmissionDao dao = new SubmissionDaoMySQL();
        return dao.getOracleSubmissionsByProblemId(problemId);
    }

    public static Submission getCorrectSubmissionsOfProblemCreator(long problemId){
        SubmissionDao dao = new SubmissionDaoMySQL();
        return dao.getCorrectSubmissionsOfProblemCreator(problemId);
    }

    public static List<Submission> getSubmissionsOfTopCoders(long problemId, int limit){
        SubmissionDao dao = new SubmissionDaoMySQL();
        return dao.getSubmissionsOfTopCoders(problemId,limit);
    }

    public static OracleSubmissions insertOracleSubmissions(OracleSubmissions oracleSubmissions){
        SubmissionDao dao = new SubmissionDaoMySQL();
        return dao.insertOracleSubmissions(oracleSubmissions);
    }

    public static void updateOracleSubmissions(OracleSubmissions oracleSubmissions){
        SubmissionDao dao = new SubmissionDaoMySQL();
        dao.updateOracleSubmissions(oracleSubmissions);
    }





}
