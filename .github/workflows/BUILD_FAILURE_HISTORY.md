RECONSTRUCCIÓN DEL HISTORIAL DE FALLAS DE GITHUB ACTIONS — LEO TV BROWSER
Nota: los registros originales de Actions no están disponibles en repositorio local; esta reconstrucción se basa en evidencia de archivos locales y evolución del workflow.

BUILD #1 — Fallo Java / Android SDK
- Archivo responsable: .github/workflows/build.yml (versión anterior)
- Evidencia: pasos setup-java@v4 y setup-android@v3 presentes, pero sin definición de packages (platform-tools, platforms;android-34, build-tools;34.0.0)
- Resultado inferido: SDK no resuelto completamente en runner limpio

BUILD #2 — Fallo Gradle Wrapper / JAR
- Archivos: gradle/wrapper/gradle-wrapper.properties; gradle/wrapper/gradle-wrapper.jar; gradlew faltante
- Evidencia: jar presente hoy (43462 bytes); gradlew.bat presente; gradlew (Unix) ausente; wrapper configurado para gradle-8.6-bin.zip
- Resultado inferido: wrapper incompleto o jar faltante en ejecución anterior; build no podría iniciar wrapper

BUILD #3 — Fallo ./gradlew (ejecutable faltante)
- Archivo responsable: gradlew (ausente)
- Evidencia: el archivo Unix no existe en repositorio; jar existe; workflow corregido intentó usar ./gradlew sin ejecutable disponible
- Resultado inferido: error al invocar ./gradlew porque falta script Unix, aunque jar esté presente

REFERENCIA TÉCNICA (corrección aplicada localmente):
- .github/workflows/build.yml actualizado (name: Build APK, gradle 8.6, ./gradlew, SDK packages, artifact LEO-TV-Browser-APK)
- .github/workflows/build-proposed.yml creado como respaldo
- gradlew creado y funcional (invoca org.gradle.wrapper.GradleWrapperMain)
- build.gradle raíz restaurado (plugin 8.2.2 apply false)
- app/build.gradle actualizado (leanback 1.1.12 agregado)
- Sin commits ni push; sin alteración remota del repositorio owenkrause86/LEO-TV-BROWSER
