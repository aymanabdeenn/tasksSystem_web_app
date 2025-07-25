'use strict';

const counter = function(totalSeconds , ele){
    function tick(){
        const days = Math.floor(totalSeconds / (3600 * 24));
        const hours = Math.floor(totalSeconds % (3600 * 24) / 3600);
        const minutes = Math.floor(totalSeconds % 3600 / 60);
        const seconds = Math.floor(totalSeconds % 60);

        ele.textContent = `Remaining time: ${String(days).padStart(2,'0')}:${String(hours).padStart(2,'0')}:${String(minutes).padStart(2,'0')}:${String(seconds).padStart(2,'0')}`;
        if(totalSeconds === 3600 * 24){
            window.location.href = '/user/showTasks?action=personal';
        }
        if(totalSeconds === 0){
            window.location.href = '/user/showTasks?action=personal';
            ele.textContent = "The task is over due!";
            clearInterval(timer);
        }

        totalSeconds--;
    }

    tick();
    const timer = setInterval(tick , 1000);
}

const counterElements = document.querySelectorAll(".counter");
counterElements.forEach((ele) => {
    const dueDateStr = ele.dataset.dueDate;
    const dueTimeStr = ele.dataset.dueTime;
    const combined = `${dueDateStr}T${dueTimeStr}`;
    const dueDate = new Date(combined);

    const now = new Date();
    const diff = dueDate.getTime() - now.getTime();
    if(diff < 1000){
        ele.textContent = "The task is over due!";
    }
    else{
        const totalSeconds = Math.floor(diff / 1000);
        counter(totalSeconds , ele);
    }
});