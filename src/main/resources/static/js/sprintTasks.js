let errandItem = document.getElementById("propErrandItem");
let checkList = document.getElementById("check-list");


let tasks = document.getElementsByClassName("task");
let taskProperty = document.getElementById("task-property");
let nameOfTask = document.getElementById("propNameOfTask");
let closeButtonTaskDesc = document.getElementById("propCloseBtn");
let propTaskName = document.getElementById("propTaskName");
let propTaskDescription = document.getElementById("propTaskDescription");
let propTaskColumnName = document.getElementById("propTaskColumnName");


let propBtnCreateTask = document.getElementById("propBtnCreateTask");
let taskMakingContent = document.getElementById("taskMakingContent");
let propTaskAddingText = document.getElementById("propTaskAddingText");
let propTaskMakingCancel = document.getElementById("propTaskMakingCancel");
let propBtnCreateAccept = document.getElementById("propBtnCreateAccept");
let errandProgressBar = document.getElementById("errandProgressBar");
let progressBar = document.getElementById("progressBar");

let taskId;
let projectId;
let counter = 0;


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
        projectId = taskById.id;

        checkList.innerHTML = "";
        errandProgressBar.ariaValuenow = 0;
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
        let taskChecked = task.finished;
        let taskClass = new ErrandTask(taskId, taskText, taskChecked);
        let errandTask = taskClass.htmlElement;
        checkList.appendChild(errandTask);
    }
    let checkboxElements = document.getElementsByClassName("errandCheckbox");
    let checkboxList = document.getElementsByClassName("checkboxList");
    progressBarState(checkboxList);

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
            let checkboxList = document.getElementsByClassName("checkboxList");
            progressBarState(checkboxList);
            setErrandChecked(item.id, element.checked);
            //progressBarState(tasks);
        }
    }


    function progressBarState(tasks) {
        let sizeOfTask = tasks.length;
        let taskDone = 0;
        let percentOfDone = 0;
        let errandProgressBar = document.getElementById('errandProgressBar');
        let taskProgressBarValue = document.getElementById('taskProgressVal' + projectId);
        let taskBar = document.getElementById('taskProgressBar' + projectId);
        let badge = document.getElementById('badge' + projectId);
        errandProgressBar.ariaValuenow = 0;


        for (let i = 0; i < tasks.length; i++) {
            if (tasks[i].finished === true || tasks[i].checked == true) {
                taskDone++;
            }
        }
        percentOfDone = Math.round(taskDone / sizeOfTask * 100);

        if (tasks.length > 0) {
            taskBar.hidden = false;
            badge.hidden = false;
            errandProgressBar.hidden = false;

            progressBar.style.width = percentOfDone + "%";
            progressBar.ariaValuenow = percentOfDone;
            progressBar.innerText = percentOfDone + "%";

            taskProgressBarValue.style.width = percentOfDone + "%";
            taskProgressBarValue.ariaValuenow = percentOfDone;
            taskProgressBarValue.innerText = percentOfDone + "%";

            badge.innerText = taskDone + '/' + sizeOfTask;

        } else {
            taskBar.hidden = true;
            badge.hidden = true;
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
        dataType: 'json',
        success: function (dataReq, status) {
            checkList.innerText = '';
            addTaskToChecklist(dataReq);
        }
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
        dataType: 'json',
        success: function (dataReq, status) {
            checkList.innerText = '';
            addTaskToChecklist(dataReq);
        }
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
        dataType: 'json',
        success: function (dataReq, status) {
            checkList.innerText = '';
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
        errandInput.className = "custom-control-input checkboxList";
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
