 function performGetRequest1(){
    var resultElement = document.getElementById('getResult1');
    resultElement.innerHTML = '';

    axios.get('http://localhost:8080/api/todot')
        .then(function (response) {
                console.log(response.data);
                console.log(response.status);
                console.log(response.statusText);
                console.log(response.headers);
            resultElement.innerHTML = generateSuccessHTMLOutput(response);
        })
        .catch(function (error) {
            resultElement.innerHTML = generateErrorHTMLOutput(error);
        });
}

function isDone(done) {
    if (done == false) {
        return 'not done';
    } else {
        return 'done';
    }
}
function generateSuccessHTMLOutput(response) {


    return ` ${response.data.map(function (todo) {
        return `
        <ul>
        <li>
        <span class>${todo.id}</span>
        ${todo.task}
        <span> / status: ${isDone(todo.done)}</span>
         </li>
        </ul>
        `
    }).join('')}`;
}
function generateErrorHTMLOutput(error) {
    return  '<h4>Result:</h4>' +
        '<h5>Message:</h5>' +
        '<pre>' + error.message + '</pre>' +
        '<h5>Status:</h5>' +
        '<pre>' + error.response.status + ' ' + error.response.statusText + '</pre>' +
        '<h5>Headers:</h5>' +
        '<pre>' + JSON.stringify(error.response.headers, null, '\t') + '</pre>' +
        '<h5>Data:</h5>' +
        '<pre>' + JSON.stringify(error.response.data, null, '\t') + '</pre>';
}

document.getElementById('todoInputForm').addEventListener('submit', performPostRequest);
function performPostRequest(e) {
    var resultElement = document.getElementById('postResult');
    var todoTitle = document.getElementById('todoTitle').value.toString();
    console.log(resultElement);
    resultElement.innerHTML = '';
    axios.post('http://localhost:8080/api/todot/', {
        task: `${todoTitle}`
    })
        .then(function (response) {
            resultElement.innerHTML = `Uusi tehtävä '${todoTitle}' on lisätty.`;
        })
        .catch(function (error) {
            console.log(todoTitle);
            console.log(resultElement);
            resultElement.innerHTML = generateErrorHTMLOutput(error);
        });
    e.preventDefault();
}

function performDelete() {
    var resultElement = document.getElementById('getResult2');
    var todoId = document.getElementById('todoDelete').value;
    resultElement.innerHTML = '';

    axios.delete(`http://localhost:8080/api/todot/${todoId}`)
        .then(function (response) {
            console.log(response);
            resultElement.innerHTML = `Tehtävä poistettu: ${todoId}`;
        })
        .catch(function (error) {
            resultElement.innerHTML = generateErrorHTMLOutput(error);
        });
}

function clearOutput() {
    var resultElement = document.getElementById('getResult1');
    resultElement.innerHTML = '';
    var resultElement = document.getElementById('getResult2');
    resultElement.innerHTML = '';
    var resultElement = document.getElementById('postResult');
    resultElement.innerHTML = '';
}


