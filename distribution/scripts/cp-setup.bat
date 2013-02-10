@echo Processing directory %1

@for %%i in (%1\*.jar) do @call cp-append.bat %%i

@echo Actual classpath: %CP%