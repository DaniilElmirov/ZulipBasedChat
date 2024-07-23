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

[*Видео работы приложения*](https://drive.google.com/file/d/1KJGMvJ_Psrog6HdY0-DwC9HGhxH-ZK3l/view?usp=sharing)
