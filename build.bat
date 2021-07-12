@echo off
cd C:\Users\eggsn\Documents\java\ExplosiveArrowPlugin
mvn package && xcopy C:\Users\eggsn\Documents\java\ExplosiveArrowPlugin\target\ExplosiveArrowPlugin-%1-SNAPSHOT.jar C:\Users\eggsn\Desktop\world_1\plugins\ExplosiveArrowPlugin.jar