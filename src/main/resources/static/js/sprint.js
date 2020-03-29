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
let errandProgressBar = document.getElementById("errandProgressBar");
let progressBar = document.getElementById("progressBar");

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

    progressBarState(tasks);

    for (let i = 0; i < tasks.length; i++) {
        let task = tasks[i];
        let taskId = task.id;
        let taskText = task.text;
        let taskChecked = task.finished;
        let taskClass = new ErrandTask(taskId, taskText, taskChecked);
        let errandTask = taskClass.htmlElement;

        checkList.appendChild(errandTask);
    }

    let checkboxElements = document.getElementsByClassName("errandCheckbox");

    for (let i = 0; i < checkboxElements.length; i++) {
        let item = checkboxElements.item(i);
        item.firstChild.onclick = function (checkbox) {
            let child = item.lastChild;
            let element = checkbox.toElement;

            if (element.checked === true) {
                child.style.textDecoration = "line-through";
            } else {
                child.style.textDecoration = "";
            }
            setErrandChecked(item.id, element.checked);
            //progressBarState(tasks);
        }
    }

    function progressBarState(tasks) {
        let sizeOfTask = tasks.length;
        let taskDone = 0;
        let percentOfDone = 0;

        for (let i = 0; i < tasks.length; i++) {
            if (tasks[i].finished === true) {
                taskDone++;
            }
        }

        percentOfDone = taskDone / sizeOfTask * 100;

        if (tasks.length > 0) {
            errandProgressBar.hidden = false
            progressBar.style.width = percentOfDone + "%";
            progressBar.ariaValuenow = percentOfDone;
            progressBar.innerText = percentOfDone + "%";
        } else {
            errandProgressBar.hidden = true;
        }
    }

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

function setErrandChecked(errandId, checked) {
    let data = {};
    data["errandId"] = errandId;
    data["checked"] = checked;

    $.ajax({
        type: "GET",
        contentType: "application/json",
        url: "setErrandChecked",
        data: data,
        dataType: 'json'
        // success: function (dataReq, status) {
        //     addTaskToChecklist(dataReq);
        // }
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
        if (this.checked === true) {
            errandInput.checked = true;
        }
        let errandLabel = document.createElement('label');
        errandLabel.className = "custom-control-label";
        errandLabel.id = "label" + this.id;
        errandLabel.htmlFor = "input" + this.id;
        errandLabel.textContent = this.text;
        if (this.checked === true) {
            errandLabel.style.textDecoration = "line-through";
        }

        item.appendChild(errandInput);
        item.appendChild(errandLabel);
        return item;
    }
}


