@echo off
cd C:\Users\eggsn\Documents\java\ExplosiveArrowPlugin
mvn package && xcopy %HOMEDRIVE%%HOMEPATH%\Documents\java\ExplosiveArrowPlugin\target\ExplosiveArrowPlugin-%1-SNAPSHOT.jar %HOMEDRIVE%%HOMEPATH%\Desktop\world_1\plugins\ExplosiveArrowPlugin.jar && xcopy target\ExplosiveArrowPlugin-%1-SNAPSHOT.jar server\plugins\ExplosiveArrowPlugin.jar