# Readme

## Описание

Этот проект представляет собой консольное приложение на Java, которое читает изображение из файла и отображает его в консоли, заменяя определенные цвета символами.

## Инструкции по использованию

### Установка и запуск

1. Скомпилируйте приложение, выполнив `javac -d target src/edu/school21/printer/*/*.java`
2. Скопируйте ресурсы из src в target `cp -R src/resources target/.`
3. Создайте архив `jar cfm ./target/images-to-chars-printer.jar src/manifest.txt -C target/ .`
4. Настройте права доступа `chmod u+x target/images-to-chars-printer.jar`
5. Наконец запустите! `java -jar target/images-to-chars-printer.jar`

#### Или вы можете запустить файл `start.sh`

### Использование программы

1. Введите символы для замены цветов при отображении изображения.
2. Введите полный путь до файла с изображением c расширением `BMP`, которое вы хотите отобразить, или введите `1`, чтобы использовать стандартное изображение.
3. Приложение вызовет метод `Controller.paint`, который в свою очередь вызовет метод `Painter.printMass` с обработанным изображением, который отрисует изображение в консоли.

## Классы

### Main

Основной класс, содержащий метод `main`. Отвечает за ввод символов и пути к файлу.

### Controller

Класс, который управляет отображением изображения. Вызывает методы других классов для выполнения операций.

### Painter

Класс, отвечающий за вывод изображения в консоль с заменой цветов.

### Parser

Класс, который выполняет чтение изображения из файла. Метод `parseFile` преобразует изображение в двумерный массив байтов.

## Важно

- Если введен путь к файлу, который не существует, или произошла ошибка во время чтения файла, будет выведено сообщение об ошибке.
