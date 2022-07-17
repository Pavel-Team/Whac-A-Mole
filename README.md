# Whac-A-Mole
Игра, разработанная за 24 часа!<br/>
Поймай кротов за ограниченное время, покажи свои гены охотника!
____
# Используемый стек и технологии:
✅ **Kotlin** - Основной язык программирования<br/>
✅ **Navigation component** - Для удобной навигации между фрагментами (приложение построено по принципу SingleActivity)<br/>
✅ **ViewModel** - Для сохранения состояния экранов<br/>
✅ **LiveData** - Для мгновенного получения изменений состояния экранов<br/>
✅ **CustomView** - Для создания собственного View игрового поля<br/>
<br/>
Для данной игры был применен подход с использованием _кастомной View_, так как другие альтернативы (например, использование Surface), являются в данном случае неэффективными (потребляют много ресурсов на переотрисовку экрана с большим значением fps).<br/>
____
# Что можно добавить, будь проект несколько больше:
✅ **DataBinding** - Для удобства отслеживания изменений состояния приложения<br/>
✅ **JUnit тестирование** - Для тестирования написанного кода<br/>
✅ **Локализация** - Для поддержки игры на нескольких языках<br/>
____
P.S. Вы можете настроить игру под себя, изменив файл app/src/main/java/ru/cpt/android/whac_a_mole/util/Constants.kt<br/>
<br/>
Приятной игры!😎
