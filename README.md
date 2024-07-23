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

[*Видео работы приложения*](https://drive.google.com/file/d/1KJGMvJ_Psrog6HdY0-DwC9HGhxH-ZK3l/view?usp=sharing)

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

[*Видео работы приложения*](https://drive.google.com/file/d/1oFFws2r4IgB-tqETMLM_jcWNKNPFSDmo/view?usp=sharing)

---
## 3. Домашнее задание по лекции "RecyclerView"

### Выполнено:
- Экран чата.
  - Экран со списком сообщений и разделителями по датам.
  - Поле ввода сообщения с отображением крестика/самолетика.
  - При нажатии на отправить новое сообщение добавляется в список.
- Экран с выбором смайликов (BottomSheetDialog).
  - Открывается при долгом клике на сообщение.
  - При клике на смайл диалог закрывается и смайл отображается как реакция под сообщением.
- Реакции под сообщением.
  - "Плюс" в списке реакций появляется когда уже хоть одна реакция есть. До этого реакции добавляются только через лонг тап.

[*Видео работы приложения*](https://drive.google.com/file/d/14F6AakCko07r3dy4JIMWlP1hX3j--FCr/view?usp=sharing)


---
## 4. Домашнее задание по лекции "Fragments"

### Выполнено:
- Использовал BottomNavigationView на MainFragment
- Первый экран.
  - На экране 2 вкладки: стримы на которые подписан пользователь, все стримы.
  - При клике на стрелку у стрима раскрывается список топиков.
  - При клике на топик открывается экран с чатом.
- Второй экран.
  - Список контактов с имитацией загрузки данных.
  - При клике на контакт открывается экран с информацией о нем.
- Третий экран.
  - Экран с деталями профиля пользователя.

[*Видео работы приложения*](https://drive.google.com/file/d/1I2TI_1IFKsxOLjng6wZbT2RSJeB3OUnq/view?usp=sharing)


---
## 5. Домашнее задание по лекции "Асинхронное взаимодействие + RxJava + Coroutines"

### Выполнено:
- Все длительные операции выполняются асинхронно.
  - Использовал корутины.
  - Поиск стримов, DiffUtils, загрузки (сообщения, профиль, список контактов, стримы).
- Реализован поиск по списку стримов на вкладках подписок и всех стримов.
  - Поиск не дергает повторно методы выдачи результата если не был изменен поисковой запрос.
  - При поиске фильтруются пустые строки и лишние пробелы.
  - При быстром вводе текста в поисковое поле не происходит выдача результата на каждый символ.
- Обработка ошибок.
  - На экране контактов сообщение и кнопка для повторной отправки запроса (добавил временную кнопку для получения запроса с ошибкой).
  - На экране чата снекбар (состояние ошибки вызывается при вводе "err" в поле ввода).
  - На остальных экранах есть заготовка под обработку ошибок.
- Загрузка.
  - Состояние загрузки на экранах стримов, профиля, списка контактов, отображается через шиммер.

[*Видео работы приложения*](https://drive.google.com/file/d/1GIAH-v9ofWqYAbE0LMry1Yy8qY7ILryi/view?usp=sharing)


---
## 6. Домашнее задание по лекции "Работа с сетью"

### Выполнено:
- Стримы, топики, сообщения в топиках, профили, статус пользователя получаются через API.
- Сообщения и реакции отправляются через API.
- Добавление хэдэра авторизации через интерсептор.
- Согласно требованиям:
  - Текстовый статус "In a meeting" у пользователя уберан из дизайна.
  - Вместо статуса online добавлены статусы active(зеленый)/idle(оранжевый)/offline(красный).
  - Кнопка "Log out" убрана из дизайна.
  - Для запроса в сеть используются Retrofit + Coroutines.

[*Видео работы приложения*](https://drive.google.com/file/d/18hT5DREb9mHbbzYHgJ5y4zqLaJhFboNW/view?usp=sharing)


---
## 7. Домашнее задание по лекции "Архитектура"

### Выполнено:
- Использовал библиотеку TEA.
- Переписал с MVVM на ELM.
- Добавил обработку состояния ошибки на всех экранах.


---
## 8. Домашнее задание по лекции "Dagger2"

### Выполнено:
- Компонент с ЖЦ всего приложения.
- Для каждого экрана созданы Subcomponent.
