# Herramientas Aivy - Suite de utilidades ligeras

## Identificador de la aplicación
- applicationId: `erasom.herramientas.aivy`

## Cómo abrir
1. Abre Android Studio.
2. Selecciona "Open" y busca la carpeta:
   `D:\Antigravity\20@erasomlabs\herramientas.aivy\01_src`
3. Espera a que Gradle sincronice (descarga zxing para QR, etc.).
4. Conecta un dispositivo Android o inicia un emulador.
5. Haz clic en Run (▶).

## Secciones incluidas (12)

| # | Sección | Descripción |
|---|---------|-------------|
| 1 | Menú | Grid principal con las 12 herramientas |
| 2 | Bloc de notas | Editor de texto con guardado persistente |
| 3 | ChatBot IA | Interfaz de chat (conectar API de IA) |
| 4 | Calendario | Calendario visual con selección de fecha |
| 5 | Link a WhatsApp | Genera enlace wa.me con número y mensaje |
| 6 | Calculadora | Calculadora básica con operaciones |
| 7 | Bloc de dibujos | Lienzo de dibujo con colores |
| 8 | Lector QR | Escáner de códigos QR con zxing |
| 9 | Mapas | Búsqueda de lugares en Google Maps |
| 10 | Convertidor de unidades | Longitud, peso, temperatura, volumen |
| 11 | Lector PDF | Visor de PDF con PdfRenderer nativo |
| 12 | Panel LED | Pantalla LED con texto en rojo |

## Notas
- Las herramientas usan APIs nativas de Android (sin dependencias pesadas).
- AdMob se puede agregar cuando tengas el ID.
- La firma de la APK debe usar la misma upload key de Google Play.
- Para conectar el ChatBot IA, reemplaza el placeholder en ChatBotActivity.kt
  con tu llamada a la API de IA (OpenAI, Gemini, etc.).
