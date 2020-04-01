var windowToDo = document.getElementById("newTask");
var btnToDo = document.getElementById("buttonToDo");
var btnInProgress = document.getElementById("buttonInProgress");
var btnDone = document.getElementById("buttonDone");
var btnCreateTask = document.getElementById("createTask");
var btnClick;
var close = document.getElementById("newTaskCloseBtn");

var dropTarget = document.querySelector(".wrapper");
var dropElements = document.querySelectorAll(".task");




(function () {
    'use strict';
    window.addEventListener('load', function () {
        // Fetch all the forms we want to apply custom Bootstrap validation styles to
        var forms = document.getElementsByClassName('needs-validation');
        // Loop over them and prevent submission
        var validation = Array.prototype.filter.call(forms, function (form) {
            form.addEventListener('submit', function (event) {
                if (form.checkValidity() === false) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    }, false);
})();


for (let i = 0; i < dropElements.length; i++) {
    dropElements[i].addEventListener("dragstart", function (ev) {
        ev.dataTransfer.setData("srcId", ev.target.id);
    });
}


dropTarget.addEventListener("dragover", function (ev) {
    ev.preventDefault();
});


dropTarget.addEventListener("drop", function (ev) {
    ev.preventDefault();
    let target = ev.target;
    if (target.className !== 'table_box') {
        if (target.className !== 'task') {
            return;
        }
        target = target.parentElement.parentNode.parentNode;
    }
    let srcId = ev.dataTransfer.getData("srcId");

    postTask(target.id, ev.dataTransfer.getData("srcId"));

    target.appendChild(document.getElementById(srcId));
});


btnToDo.onclick = function () {
    windowToDo.style.display = "block";
    btnClick = "ToDo"

};

btnInProgress.onclick = function () {
    windowToDo.style.display = "block";
    btnClick = "InProgress"
};

btnDone.onclick = function () {
    windowToDo.style.display = "block";
    btnClick = "Done"

};

close.onclick = function () {
    windowToDo.style.display = "none";
};

window.onclick = function (event) {
    if (event.target === windowToDo) {
        windowToDo.style.display = "none";
    }
};


btnCreateTask.onclick = function () {
    var taskProgress = document.getElementById("taskProgress");
    // var sprintId = document.getElementById("sprintId");

    if (btnClick === "ToDo") {
        taskProgress.value = "ToDo";
    }
    if (btnClick === "InProgress") {
        taskProgress.value = "InProgress";
    }
    if (btnClick === "Done") {
        taskProgress.value = "Done";
    }

    const urlParams = new URLSearchParams(window.location.search);
    //sprintId.value = urlParams.get('sprintId');
};
//
// let tasksProgressBar = document.getElementsByClassName("task-progress-style-inline");
// for (let i = 0; i < tasksProgressBar.length; i++) {
//     tasksProgressBar[i].hidden = true;
// }


// function progressBarState(tasks) {
//     let sizeOfTask = tasks.length;
//     let taskDone = 0;
//     let percentOfDone = 0;
//
//     for (let i = 0; i < tasks.length; i++) {
//         if (tasks[i].finished === true || tasks[i].checked == true) {
//             taskDone++;
//         }
//     }
//
//     percentOfDone = Math.round(taskDone / sizeOfTask * 100);
//
//     if (tasks.length > 0) {
//         errandProgressBar.hidden = false;
//         progressBar.style.width = percentOfDone + "%";
//         progressBar.ariaValuenow = percentOfDone;
//         progressBar.innerText = percentOfDone + "%";
//     } else {
//         errandProgressBar.hidden = true;
//     }
// }

window.onload = myFunction();

function myFunction() {
    for (let i = 0; i < tasksList.length; i++) {

        let taskValidity = document.getElementById('taskValidity' + tasksList[i].id);
        let taskValidityNumb = document.getElementById('taskValidityNumb' + tasksList[i].id);

        taskValidityText(taskValidity, taskValidityNumb);

        let taskBar = document.getElementById('taskProgressBar' + tasksList[i].id);
        let taskProgressBarValue = document.getElementById('taskProgressVal' + tasksList[i].id);
        let badge = document.getElementById('badge' + tasksList[i].id);
        let taskErrands = tasksList[i].taskErrands;
        let taskErrandsSize = taskErrands.length;
        let taskDone = 0;

        if(taskErrandsSize > 0 ) {
            for (let i = 0; i < taskErrandsSize; i++) {
                if (taskErrands[i].finished === true || taskErrands[i].checked == true) {
                    taskDone++;
                }
            }
            taskBar.hidden = false;
            badge.hidden = false;
            let taskPercentProgress = Math.round(taskDone / taskErrandsSize * 100);
            taskProgressBarValue.style.width = taskPercentProgress + "%";
            taskProgressBarValue.ariaValuenow = taskPercentProgress;
            taskProgressBarValue.innerText = taskPercentProgress + "%";
            badge.innerText = taskDone + '/' + taskErrandsSize;
        }else {
            taskBar.hidden = true;
            badge.hidden = true;
        }
        console.log("RUN...");
    }
}


function taskValidityText(taskValidity, validityValue){

    let trivialColor = "#59b92e";
    let easyColor = "#0da030";
    let mediumColor = "#d2ad4f";
    let hardColor = "#da1269";
    let extraHardColor = "#ff0000";

    if(validityValue.innerText === '1'){
        taskValidity.style.backgroundColor = trivialColor;
        taskValidity.innerText = "TRIVIAL";
    }
    if(validityValue.innerText === '2'){
        taskValidity.style.backgroundColor = easyColor;
        taskValidity.innerText = "EASY";
    }
    if(validityValue.innerText === '3'){
        taskValidity.style.backgroundColor = mediumColor;
        taskValidity.innerText = "MEDIUM";
    }
    if(validityValue.innerText === '4'){
        taskValidity.style.backgroundColor = hardColor;
        taskValidity.innerText = "HARD";
    }
    if(validityValue.innerText === '5'){
        taskValidity.style.backgroundColor = extraHardColor;
        taskValidity.innerText = "EXTRA HARD";
    }
}




