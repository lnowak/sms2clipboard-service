@title Password-Less-Service %DATE% %TIME%  

@echo
@echo Setting up the classpath:
@echo ------------------------
@set CP=.
@set CP=%CP%;config
@call cp-setup.bat lib
@call cp-setup.bat lib\dependencies
@echo ------------------------
@echo

@set JAVA_OPTS=-Xmx256m -Dfile.encoding=UTF-8
@set DEBUG_OFF=-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=6006

"%JAVA_HOME%\bin\java" %DEBUG% %JAVA_OPTS% -cp %CP% Main