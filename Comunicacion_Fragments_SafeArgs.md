# 📲 Comunicación entre Fragments usando SafeArgs en Android

Este documento explica cómo comunicar datos entre `Fragments` usando **SafeArgs** en Android. Usaremos `FirstFragment` para enviar datos y `SecondFragment` para recibirlos a través del archivo `nav_graph.xml`.

---

## 🧭 1. Definir Argumentos en el Navigation Graph

Ubica el archivo `nav_graph.xml` en la ruta:

```
res/navigation/nav_graph.xml
```

Dentro de tu `SecondFragment`, haz lo siguiente:

1. Selecciona el fragmento `secondFragment`.
2. En el panel derecho, busca la opción **Arguments**.
3. Agrega un nuevo argumento que deseas recibir desde el `firstFragment`.

---

## ⚙️ 2. Habilitar SafeArgs

### a. Configuración en `build.gradle.kts (Project)`

Agrega el siguiente plugin en el archivo `build.gradle.kts` del **nivel del proyecto** (no del módulo):

```kotlin
buildscript {
    repositories {
        google()
    }
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.0")
    }
}
```

> 🔄 *Nota:* Si el IDE te sugiere reemplazar con la versión existente del catálogo, acepta la sugerencia para mantener la coherencia del proyecto.

---

### b. Configuración en `build.gradle.kts (Module: app)`

Agrega el plugin de SafeArgs en la sección `plugins`:

```kotlin
plugins {
    id("androidx.navigation.safeargs")
}
```

---

## ✅ 3. Resultado Esperado

Con esta configuración, podrás:

- Generar clases `Directions` para cada fragmento.
- Pasar argumentos de forma **segura y tipada** entre `Fragments`.

---

## 📚 Recursos adicionales

- [Documentación oficial de Navigation Component](https://developer.android.com/guide/navigation)
- [SafeArgs - Guía oficial](https://developer.android.com/guide/navigation/navigation-pass-data#Safe-args)

---

## 🧑‍💻 Ejemplo práctico (opcional)

Si deseas, puedes agregar un ejemplo aquí con código de cómo pasar un string desde el `FirstFragment` y recuperarlo en el `SecondFragment`.

---

¡Listo! Ahora tu proyecto está preparado para usar **SafeArgs** y comunicar datos entre `Fragments` de manera segura y estructurada.
