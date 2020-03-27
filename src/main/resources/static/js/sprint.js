var windowToDo = document.getElementById("newTask");
var btnToDo = document.getElementById("buttonToDo");
var btnInProgress = document.getElementById("buttonInProgress");
var btnDone = document.getElementById("buttonDone");
var btnCreateTask = document.getElementById("createTask");
var btnClick;
var close = document.getElementById("newTaskCloseBtn");

var dropTarget = document.querySelector(".wrapper");
var dropElements = document.querySelectorAll(".task");

let tasks = document.getElementsByClassName("task");
let taskProperty = document.getElementById("task-property");
let nameOfTask = document.getElementById("propNameOfTask");
let closeButtonTaskDesc = document.getElementById("propCloseBtn");
let propTaskName = document.getElementById("propTaskName");
let propTaskDescription = document.getElementById("propTaskDescription");
let propTaskColumnName = document.getElementById("propTaskColumnName");

let errandItem = document.getElementById("propErrandItem");
let checkList = document.getElementById("check-list");

let propBtnCreateTask = document.getElementById("propBtnCreateTask");
let taskMakingContent = document.getElementById("taskMakingContent");
let propTaskAddingText = document.getElementById("propTaskAddingText");
let propTaskMakingCancel = document.getElementById("propTaskMakingCancel");
let propBtnCreateAccept = document.getElementById("propBtnCreateAccept");
let taskId;
let counter = 0;


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


propBtnCreateTask.onclick = function () {
    taskMakingContent.hidden = false;
    propTaskAddingText.focus();
    propTaskAddingText.select();
};


propTaskMakingCancel.onclick = function () {
    taskMakingContent.hidden = true;
};


propBtnCreateAccept.onclick = function () {
    taskMakingContent.hidden = true;
    createTaskErrand(taskId, propTaskAddingText.value);
    propTaskAddingText.value = "";
};


function getTaskById(id) {
    return tasksList.find(x => x.id.toString() == id);
}


for (let i = 0; i < tasks.length; i++) {
    tasks[i].onclick = function () {
        taskProperty.style.display = "block";
        let taskById = getTaskById(tasks[i].id);
        getTaskErrands(taskById.id);
        checkList.innerHTML = "";
        propTaskName.value = taskById.name;
        propTaskDescription.value = taskById.description;
        propTaskColumnName.text = taskById.progres;
        taskId = taskById.id;
    }
}


closeButtonTaskDesc.onclick = function () {
    taskProperty.style.display = "none";
};


function generateTask(text) {
    counter++;
    let data = {};
    data["id"] = "id" + counter;
    data["text"] = text;
    return new Array(data);
}


function addTaskToChecklist(tasks) {
    for (let i = 0; i < tasks.length; i++) {
        let task = tasks[i];
        let taskId = task.id;
        let taskText = task.text;
        let taskClass = new ErrandTask(taskId, taskText, false);
        let errandTask = taskClass.htmlElement;

        checkList.appendChild(errandTask);
    }

    let checkboxElements = document.getElementsByClassName("errandCheckbox");

    function checkedListCheck(checkboxElements) {
        console.log(checkboxElements);
    }
}


function createTaskErrand(taskId, taskErrandText) {
    let data = {};
    data["taskId"] = taskId;
    data["taskErrandText"] = taskErrandText;

    let taskToAdd = generateTask(taskErrandText)

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "addTaskErrand",
        data: data,
        dataType: 'json'
    });

    addTaskToChecklist(taskToAdd);
}


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


function getTaskErrands(taskId) {
    let data = {};
    let taskErrandsData;
    data["taskId"] = taskId;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "getTaskErrands",
        data: data,
        dataType: 'json',
        success: function (dataReq, status) {
            addTaskToChecklist(dataReq);
        }
    });
}

class ErrandTask {
    constructor(id, text, checked) {
        this.id = id;
        this.text = text;
        this.checked = checked;
    }

    get htmlElement() {
        let item = document.createElement("div");
        item.id = this.id;
        item.className = "custom-control custom-checkbox check-list-styling errandCheckbox";

        let errandInput = document.createElement('input');
        errandInput.type = 'checkbox';
        errandInput.className = "custom-control-input";
        errandInput.id = "input" + this.id;
        let errandLabel = document.createElement('label');
        errandLabel.className = "custom-control-label";
        errandLabel.id = "label" + this.id;
        errandLabel.htmlFor = "input" + this.id;
        errandLabel.textContent = this.text;
        item.appendChild(errandInput);
        item.appendChild(errandLabel);
        return item;
    }
}


