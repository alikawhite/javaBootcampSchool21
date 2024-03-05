#!/bin/bash

downloadLibs() {
    mkdir lib
    curl https://repo1.maven.org/maven2/com/beust/jcommander/1.72/jcommander-1.72.jar -o lib/jcommander-1.72.jar 2> /dev/null
    curl https://repo1.maven.org/maven2/com/diogonunes/JCDP/4.0.0/JCDP-4.0.0.jar -o lib/JCDP-4.0.0.jar 2> /dev/null
}

deleteLibs() {
    rm -rf target && rm -rf lib
}

startProgram() {
    echo "Нужно выбрать два цвета, например: RED, GREEN, BLUE, CYAN"
    read -p "Введите цвета через пробел: " color1 color2
    java -jar target/images-to-chars-printer.jar --white=$color1 --black=$color2
}

installLibs() {
    mkdir target
    cd target
    jar xf ../lib/jcommander-1.72.jar
    jar xf ../lib/JCDP-4.0.0.jar
    rm -rf META-INF
    cd ..
}

compile() {
    javac -d target -cp lib/\* src/edu/school21/printer/app/Main.java src/edu/school21/printer/logic/*.java
    cp -r src/resources ./target/resources
    jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target .
    chmod u+x target/images-to-chars-printer.jar
}

echo "1. Установить и запустить"
echo "2. Запустить"
echo "3. Установить без дополнительных библиотек (если они уже скачаны)"
echo "4. Скачать и установить библиотеки"
echo "5. Удалить приложение (с библиотеками)"

read -p "Введите нужную опцию:  " param

if [[ param -eq 1 ]]
then
    deleteLibs
    downloadLibs
    installLibs
    compile
    startProgram
fi

if [[ param -eq 2 ]]
then
    startProgram
fi

if [[ param -eq 3 ]]
then
    compile
fi

if [[ param -eq 4 ]]
then
    downloadLibs
    installLibs
fi

if [[ param -eq 5 ]]
then
    deleteLibs
fi