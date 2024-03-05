# Images to Chars Printer

### Компиляция и запуск

1. Создайте дополнительные папки

`mkdir lib && mkdir target`

2. Скачайте дополнительные библиотеки

`curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar`
`curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar`

3. Установите библиотеки

`cd target`
`jar xf ../lib/jcommander-1.72.jar`
`jar xf ../lib/JCDP-4.0.0.jar`
`rm -rf META-INF`
`cd ..`

4. Скомпилируйте исходный код:

`javac -d target -cp lib/\* src/edu/school21/printer/app/Main.java src/edu/school21/printer/logic/*.java`

5. Скопируйте ресурсы:

`cp -r src/resources ./target/resources`

6. Создайте JAR-файл и поделитесь правами доступа:

`jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .`
`chmod u+x target/images-to-chars-printer.jar`

7. Запустите программу, она принимает два аргумента – цвета, первый для отрисовки фона, второй – для буковок, пример: BLUE, WHITE, CYAN, GREEN. Подставьте их вместо слова "ЦВЕТ"

`java -jar target/images-to-chars-printer.jar --white=ЦВЕТ --black=ЦВЕТ`