# huxley-commons
Biblioteca com ferramentas comuns aos outros projetos.

* Clone o repositório
* Importe no IDEA
* No idea, clique no pom.xml e selecione "Add as maven project"
* Verifique se o mysql está rodando e se você tem o banco huxley-dev em execução
* altere o arquivo commons-conf.properties para apontar para o banco de dados correto
* Se você estiver com erros de compilacao no IDEA, vá em file -> project structure -> modules  e remova o main e o test

* Digite: mvn install


# Dicas Mysql

* instalar: sudo apt-get install mysql-server
* verifique se foi instalado e o serviço está rodando: sudo netstat -tap | grep mysql
* Conectar ao banco: mysql -u root -p
* importar arquivo de dump: mysql -u huxley -p huxley-dev < huxley-prod095030122015.sql