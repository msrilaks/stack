import { API_BASE_URL, ACCESS_TOKEN } from '../constants';

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    return fetch(options.url, options)
    .then(response => 
        response.json().then(json => {
            if(!response.ok) {
                return Promise.reject(json);
            }
            return json;
        })
    );
};

export const styles = {
    appTitle: {
        color:'#0050ef',
        fontFamily: 'Montserrat, sans-serif',
        
    },
    appLogout: {
        color:'#0050ef',
        fontFamily: 'Montserrat, sans-serif',
    },
    appHeader: {
        background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
        
    },
    stackIcon: {
    //   background: 'linear-gradient(45deg, #FE6B8B 30%, #FF8E53 90%)',
    color:'#EA005E',
    },
    stackTab: {
        indicatorColor:'#0050ef',
        textColor:'#5D5A58',
        color:'#5D5A58',
        background:'#F5F5F5',
    },
    taskIcon: {
        color:'#EA005E',
    },
    taskEmpty: {
        color:'#0050ef',
    },
    taskTextField: {
        marginRight: '10px',
    },
    taskCard: {
        background:'#F5F5F5',
    }
   
  };

export function getCurrentUser() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getStack() {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack",
        method: 'GET'
    });
}

export function login(loginRequest) {
    return request({
        url: API_BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function createTask(createTaskRequest) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+createTaskRequest.stackId+"/tasks",
        method: 'POST',
        body: JSON.stringify(createTaskRequest)
    });
}

export function deleteTask(deleteTaskRequest) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+deleteTaskRequest.stackId+"/tasks/"+deleteTaskRequest.id,
        method: 'DELETE',
        body: JSON.stringify(deleteTaskRequest)
    });
}


export function getProfilePic(userName){
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    userName = 'gajanan.mudaliar@gmail.com'; 
    return request({
        url: "https://www.google.com/m8/feeds/photos/profile/google/"+userName+"?v=3",
        method: 'GET',
    });
 
}
export function patchTask(patchTaskRequest, patchAction) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+patchTaskRequest.stackId+"/tasks/"+patchTaskRequest.id
        +"?"+patchAction,
        method: 'PATCH',
        body: JSON.stringify(patchTaskRequest)
    });
}

export function getTasks(stackId) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+stackId+"/tasks",
        method: 'GET'
    });
} 

export function getTodoTasks(stackId) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+stackId+"/tasks?isToDo=true",
        method: 'GET'
    });
} 

export function getDeletedTasks(stackId) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+stackId+"/tasks?isDeleted=true",
        method: 'GET'
    });
} 

export function getMovedTasks(stackId) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+stackId+"/tasks?isPushed=true",
        method: 'GET'
    });
} 

export function getCompletedTasks(stackId) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+stackId+"/tasks?isCompleted=true",
        method: 'GET'
    });
} 

export function getTask(stackId, taskId) {
    if(!localStorage.getItem(ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: API_BASE_URL + "/stack/"+stackId+"/tasks/"+taskId,
        method: 'GET'
    });
} 

export function signup(signupRequest) {
    return request({
        url: API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}