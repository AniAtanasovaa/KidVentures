 // Функция за извличане и визуализация на коментарите за конкретното място
async function loadCommentsForPlace(placeId) {
    try {
        const response = await fetch(`/api/comments/place/${placeId}`);
        const comments = await response.json();

        // Тук може да извършите логика за визуализация на коментарите, например, добавяне към DOM
        const commentsContainer = document.getElementById('comments-container');
        comments.forEach(comment => {
            const commentElement = document.createElement('div');
            commentElement.textContent = comment.content;
            commentsContainer.appendChild(commentElement);
        });

        // Пренасочване към comments.html
        window.location.href = '/api/comments/place/' + placeId;
    } catch (error) {
        console.error('Error:', error);
    }
}

 // Обработчик на бутона за зареждане на коментарите
 document.getElementById('loadCommentsButton').addEventListener('click', function () {
     // Извличане на идентификационния номер от атрибута th:data
     const placeId = document.getElementById('loadCommentsButton').getAttribute('data-place-id');

     // Извикване на функцията с динамично подаденото ID на място
     loadCommentsForPlace(placeId);
 });