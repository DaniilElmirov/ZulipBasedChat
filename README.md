# TFS Android Spring 2024

---
## Домашнее задание по лекции "Основные компоненты"

### Выполнено:
- На старте открывается первая Activity - из нее открывается вторая Activity с ожиданием результата.
- Из второй Activity запускается Service, который получает список контактов через ContentProvider.
- После завершения работы Service возвращает результат своей работы во вторую Activity с помощью LocalBroadcastReceiver.
- Вторая Activity после получения ответа из LocalBroadcastReceiver показывает Toast с количеством контактов, закрывается и передает результат своей работы в первую Activity.
- Первая Activity после получения ответа от второй Activity отображает результат в виде RecyclerView.
- Текущее задание в отдельном модуле внутри проекта.

[*Видео работы приложения*](https://gitlab.com/DaniilElmirov/tfs_spring_2024/-/blob/homework_1/homework_1/src/main/res/video/homework_1.webm?ref_type=heads)
