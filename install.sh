#!/bin/sh

./gradlew :app:installDebug
./gradlew :app-flavor:installProductionDebug
./gradlew :app-flavor:installDevelopmentDebug
./gradlew :app-androidformenhancer:installDebug
./gradlew :app-richbuttons:installDebug
./gradlew :app-simplealertdialog:installDebug
