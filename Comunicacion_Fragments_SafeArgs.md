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

```java
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

```java
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

`FirstFragment.java`
```java
binding.btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.txtNombre.getText().toString();
                String inputNumber = binding.txtNumero.getText().toString();
                if (username.isEmpty() || inputNumber.isEmpty()){
                    Toast.makeText(requireContext(), "campos vacios", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (inputNumber.equals(numeroCadena)){
                   FirstFragmentDirections.ActionFirstFragmentToSecondFragment action =
                           FirstFragmentDirections.actionFirstFragmentToSecondFragment(username);

                    NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(action);
                } else {
                    Toast.makeText(requireContext(), "Aleatorio incorrecto", Toast.LENGTH_SHORT).show();
                }


            }
        });

```

`SecondFragment.java`
```java
String name = SecondFragmentArgs.fromBundle(getArguments()).getUsername();
        binding.txtUsername.setText(name);
```

---

### Comunicación co retrofit método para obtener respuesta
```java
private void obtenerLibros(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://yerridev.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        YCRApi ycrApi = retrofit.create(YCRApi.class);
        Call <RptaObtenerLibros> call = ycrApi.obtenterLibros();
        call.enqueue(new Callback<RptaObtenerLibros>() {
            @Override
            public void onResponse(Call<RptaObtenerLibros> call, Response<RptaObtenerLibros> response) {
                if(!response.isSuccessful()){
                    binding.txtLibros.setText("Codigo: " + response.code());
                }
                RptaObtenerLibros rptaObtenerLibros =response.body();
                List<Libro> listaLibros =rptaObtenerLibros.getData();
                for(Libro libro: listaLibros){
                    String contenido = "";
                    contenido += "id: " + String.valueOf(libro.getId()) + "\n";
                    contenido += "nombre: " + libro.getNombre() + "\n";
                    contenido += "isbn: " + libro.getIsbn() + "\n";
                    binding.txtLibros.append(contenido);
                }
            }

            @Override
            public void onFailure(Call<RptaObtenerLibros> call, Throwable throwable) {

            }
        });

    }
```




¡Listo! Ahora tu proyecto está preparado para usar **SafeArgs** y comunicar datos entre `Fragments` de manera segura y estructurada.
