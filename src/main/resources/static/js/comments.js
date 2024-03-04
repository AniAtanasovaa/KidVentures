////Функцията използва jQuery за изпращане асинхронна GET заявка към URL '/comments/place/' + placeId.
// В случай на успешно зареждане на коментарите, те се вмъкват в контейнера с id 'comments-container'. Също така,
// се показва формата за добавяне на коментар. В случай на грешка при заявката, се извежда грешката в конзолата.

//Todo да направя кометарите с DOM (курс JS Front-End - февруари 2023 лекция DOM and Events 14.03)или React
async function loadCommentsForPlace(placeId) {
    $.ajax({
        type: 'GET',
        url: '/comments/place/' + placeId,
        success: function(data) {
            $('#comments-container').html(data);
            // Показвам формата за добавяне на коментар
            $('form').show();
        },
        error: function(error) {
            console.error('Error loading comments:', error);
        }
    });
}

// Обработка за бутона за зареждане на коментарите: активира се при клик върху елемент с id 'load-comments-btn'
// Получава placeId от атрибута 'data-place-id' на кликнатия елемент и след това се извиква loadCommentsForPlace(placeId).
$('#load-comments-btn').on('click', function () {
    const placeId = $(this).data('place-id');
    console.log("Loading comments for place ID:", placeId);
    loadCommentsForPlace(placeId);
});

// Изпълнение на заявката при зареждане на страницата. Този фрагмент се изпълнява при зареждане на страницата
// Получава placeId от атрибута 'data-place-id' на някой елемент и след това извиква loadCommentsForPlace(placeId).
$(document).ready(function() {
    const placeId = document.querySelector('[data-place-id]').getAttribute('data-place-id');
    loadCommentsForPlace(placeId);
});

// съхранение на URL на изображението в #comments-container, използвайки jQuery data метод.
$('#comments-container').data('image-url', $('[data-place-id]').find('img').attr('src'));

// Изпълнение на заявката при добавяне на коментар. Този обработчик се изпълнява при подаване на submit
// Използва fetch за изпращане на POST заявка към '/comments/place/' + placeId със съдържанието на формата.
// След успешно добавяне на коментар, се извиква loadCommentsForPlace(placeId) за да се заредят отново коментарите.
$('form').submit(async function(e) {
    e.preventDefault();
    const placeId = this.closest('[data-place-id]').getAttribute('data-place-id');
    console.log('Adding comment...');
    await fetch('/comments/place/' + placeId, {
        method: 'POST',
        headers: {
            'Accept': 'text/html',
            'Content-Type': 'application/x-www-form-urlencoded',
        },
        body: new URLSearchParams(new FormData(this)).toString(),
    });

    // След като добавим коментар, зареждаме отново коментарите
    console.log('Comment added. Loading comments...');
    loadCommentsForPlace(placeId);
});

// Обработчик за бутона за добавяне на коментар - изпълнява се при клик върху елемента с id 'add-comment-btn' и
// показва формата за добавяне на коментар.
$('#add-comment-btn').on('click', function () {
    // Показвам формата за добавяне на коментар
    $('form').show();
});

//jQuery е библиотека за JavaScript, която се използва за облекчаване на работата с DOM (Document Object Model),
// анимации, обработка на събития и изпращане на асинхронни заявки към сървъри. Тя предоставя прост и удобен
// синтаксис, който улеснява разработката на клиентска страна на уеб приложения.
// Основните функционалности на jQuery: 1/ DOM манипулации: jQuery предоставя лесни методи за манипулиране на HTML
// елементите и тяхната структура в DOM. 2/ Събития: Улеснява добавянето на обработчици на събития, като клик,
// изглеждане,клавишни комбинации и други. 3/ Анимации: jQuery предоставя методи за изпълнение на анимации като скриване,
// показване, изглеждане и др. AJAX заявки: Улеснява изпращането на асинхронни HTTP заявки към сървъри и обработката на
// получените данни.


//$.ajax({ ... }) е метод от библиотеката jQuery, който се използва за изпращане на асинхронни HTTP заявки
// (AJAX заявки) към уеб сървъри. Този метод предоставя удобен начин за взаимодействие със сървър и получаване
// на данни без да се налага презареждане на цялата уеб страница. Някои от ключовите параметри, които могат да бъдат
// използвани в $.ajax({ ... }):
// type: HTTP методът за заявката (например, 'GET', 'POST', 'PUT', 'DELETE' и други).
// url: Адресът на уеб ресурса, към който се отправя заявката. - В моят случай  url: '/comments/place/' + placeId,
// data: Данни, които трябва да бъдат изпратени към сървъра. Могат да бъдат представени като обект, string или
// FormData, в зависимост от HTTP метода и изискванията на сървъра.
// success: Функция, която се изпълнява при успешно приключване на заявката. Получените данни обикновено се
// използват в тази функция.
// error: Функция, която се изпълнява при възникване на грешка по време на изпълнението на заявката.
// В моя случай $.ajax({ ... }) се използва за изпращане на GET заявка към сървъра с цел зареждане на коментарите
// за конкретното място. При успешно получаване на коментарите, те се вмъкват в елемента с id 'comments-container',
// а формата за добавяне на коментар се показва. В случай на грешка се извежда съобщение за грешка в конзолата.

// AJAX - Asynchronous JavaScript And XML: Allows us to communicate with remote servers in an asynchronous way.
// With AJAX calls, we can request data from web servers dynamically