# TFS Android Spring 2024

---
## 1. Домашнее задание по лекции "Основные компоненты"

### Выполнено:
- На старте открывается первая Activity - из нее открывается вторая Activity с ожиданием результата.
- Из второй Activity запускается Service, который получает список контактов через ContentProvider.
- После завершения работы Service возвращает результат своей работы во вторую Activity с помощью LocalBroadcastReceiver.
- Вторая Activity после получения ответа из LocalBroadcastReceiver показывает Toast с количеством контактов, закрывается и передает результат своей работы в первую Activity.
- Первая Activity после получения ответа от второй Activity отображает результат в виде RecyclerView.
- Текущее задание в отдельном модуле внутри проекта.

[*Видео работы приложения*](https://gitlab.com/DaniilElmirov/tfs_spring_2024/-/blob/homework_2/homework_1/src/main/res/raw/homework_1.webm?ref_type=heads)

---
## 2. Домашнее задание по лекции "View & ViewGroup"

### Выполнено:
- Создана кастомная View(ReactionView) для отображения Emoji реакции и кол-ва реакций.
  - ReactionView поддерживает 2 состояния(выбрано/не выбрано) и меняет их по клику.
  - Есть возможность задавать из кода emoji и кол-во реакций.
- Создан FlexBoxLayout.
  - Каждый ребенок помещается в строку.
  - Если следующий ребенок не влезает в строку, то переносится на следующую.
  - Последний ребенок ImageView с иконкой "+" (с помощью нее можно добавлять Emoji).
- Создана кастомная ViewGroup(MessageLayout), которая отображает сообщение вместе с реакциями.
  - Отображает: аватар пользователя, имя пользователя, текст сообщения, реакции.
- Добавлена возможность устанавливать аватар пользователя, имя пользователя, текст сообщения из кода.

[*Видео работы приложения*](https://gitlab.com/DaniilElmirov/tfs_spring_2024/-/blob/homework_2/app/src/main/res/raw/homework_2.mp4?ref_type=heads)