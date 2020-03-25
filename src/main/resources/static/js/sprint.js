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


var dropTarget = document.querySelector(".wrapper");
var dropElements = document.querySelectorAll(".task");

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


function postTask(tableName, taskId) {
    var data = {};
    data["taskId"] = taskId;
    data["tableName"] = tableName;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "pageChange",
        data: data,
        dataType: 'json'
    });
}


var windowToDo = document.getElementById("newTask");
var btnToDo = document.getElementById("buttonToDo");
var btnInProgress = document.getElementById("buttonInProgress");
var btnDone = document.getElementById("buttonDone");
var btnCreateTask = document.getElementById("createTask");
var btnClick;
var close = document.getElementById("newTaskCloseBtn");

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
}


let tasks = document.getElementsByClassName("task");
let taskProperty = document.getElementById("task-property");
let nameOfTask = document.getElementById("propNameOfTask");
let closeButtonTaskDesc = document.getElementById("propCloseBtn");
let propTaskName = document.getElementById("propTaskName");
let propTaskDescription = document.getElementById("propTaskDescription");
let propTaskColumnName = document.getElementById("propTaskColumnName");


//let tasksList = [[${tasksList}]];
//let tasksList = tasksList;

function getTaskById(id) {
   return tasksList.find(x => x.id.toString() == id);
}

for (let i = 0; i < tasks.length; i++) {
    tasks[i].onclick = function () {
        taskProperty.style.display = "block";
        let taskById = getTaskById(tasks[i].id);
        propTaskName.value = taskById.name;
        propTaskDescription.value = taskById.description;
        propTaskColumnName.text = taskById.progres;
    }
}

closeButtonTaskDesc.onclick = function () {
    taskProperty.style.display = "none";
}


