// import { config.API_BASE_URL, config.ACCESS_TOKEN, config.OAUTH2_REDIRECT_URI } from '../constants';
import { config } from '../constants';

const request = (options) => {
    console.log("REACT Environment is : "+process.env.REACT_APP_STAGE);
    console.log("config.API_BASE_URL : "+config.API_BASE_URL);
    console.log("config.OAUTH2_REDIRECT_URI: " + +config.OAUTH2_REDIRECT_URI);
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(config.ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(config.ACCESS_TOKEN))
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

const requestUpload = (options) => {
    console.log("REACT Environment is : "+process.env.REACT_APP_STAGE);
    console.log("config.API_BASE_URL : "+config.API_BASE_URL);
    console.log("config.OAUTH2_REDIRECT_URI: " + +config.OAUTH2_REDIRECT_URI);
    const headers = new Headers({
    })
    if(localStorage.getItem(config.ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(config.ACCESS_TOKEN))
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
    },
    taskAvatar: {
        display: 'inline-block',
        verticalAlign: 'middle',
        backgroundSize: '32px 32px',
        borderRadius: '50%',
        margin: 0,
        overflow: 'hidden',
        position: 'relative',
        height: '32px',
        width: '32px'
    },
    thumbsContainer: {
     display: 'flex',
     flexDirection: 'row',
     flexWrap: 'wrap',
     marginTop: 16
    },
    thumb: {
     display: 'inline-flex',
     borderRadius: 2,
     border: '1px solid #eaeaea',
     marginBottom: 8,
     marginRight: 8,
     width: 100,
     height: 100,
     padding: 4,
     boxSizing: 'border-box'
    },
   thumbInner: {
     display: 'inline-block',
     minWidth: 0,
     overflow: 'hidden'
   },
   img: {
     display: 'block',
     width: 'auto',
     height: '100%'
   },
   thumbIcon: {
     position: 'absolute',
     opacity: 0.4,
     transition: '.5s ease',
     backgroundSize: '50%'
   },
   photoButtonIcon: {
     padding: 0
   }
  };

export function getCurrentUser() {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/user/me",
        method: 'GET'
    });
}

export function getStack() {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack",
        method: 'GET'
    });
}

export function login(loginRequest) {
    return request({
        url: config.API_BASE_URL + "/auth/login",
        method: 'POST',
        body: JSON.stringify(loginRequest)
    });
}

export function createTask(createTaskRequest) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+createTaskRequest.stackId+"/tasks",
        method: 'POST',
        body: JSON.stringify(createTaskRequest)
    });
}

export function uploadPhoto(stackId, taskId, filename, file) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    const formData = new FormData()
    formData.append('image', file, filename)
    return requestUpload({
        url: config.API_BASE_URL +
        "/stack/"+stackId+"/tasks/"+taskId+"/photos?title="+filename,
        method: 'POST',
        body: formData
    });
}

export function getPhotos(stackId, taskId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    return request({
        url: config.API_BASE_URL +
        "/stack/"+stackId+"/tasks/"+taskId+"/photos",
        method: 'GET'
    });
}

export function deletePhoto(stackId, taskId, photoId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    return request({
        url: config.API_BASE_URL +
        "/stack/"+stackId+"/tasks/"+taskId+"/photos/"+photoId,
        method: 'DELETE'
    });
}

export function deleteTask(deleteTaskRequest) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+deleteTaskRequest.stackId+"/tasks/"+deleteTaskRequest.id,
        method: 'DELETE',
        body: JSON.stringify(deleteTaskRequest)
    });
}

export function modifyTask(modifyTaskRequest, patchAction) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+modifyTaskRequest.stackId+"/tasks/"+modifyTaskRequest.id,
        method: 'PUT',
        body: JSON.stringify(modifyTaskRequest)
    });
}

export function getProfilePic(userName){
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }
    userName = 'gajanan.mudaliar@gmail.com'; 
    return request({
        url: "https://www.google.com/m8/feeds/photos/profile/google/"+userName+"?v=3",
        method: 'GET',
    });
 
}
export function patchTask(patchTaskRequest, patchAction) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+patchTaskRequest.stackId+"/tasks/"+patchTaskRequest.id
        +"?"+patchAction,
        method: 'PATCH',
        body: JSON.stringify(patchTaskRequest)
    });
}

export function getTasks(stackId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+stackId+"/tasks",
        method: 'GET'
    });
} 

export function getTodoTasks(stackId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+stackId+"/tasks?isToDo=true",
        method: 'GET'
    });
} 

export function getDeletedTasks(stackId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+stackId+"/tasks?isDeleted=true",
        method: 'GET'
    });
} 

export function getMovedTasks(stackId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+stackId+"/tasks?isPushed=true",
        method: 'GET'
    });
} 

export function getCompletedTasks(stackId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+stackId+"/tasks?isCompleted=true",
        method: 'GET'
    });
} 

export function getTask(stackId, taskId) {
    if(!localStorage.getItem(config.ACCESS_TOKEN)) {
        return Promise.reject("No access token set.");
    }

    return request({
        url: config.API_BASE_URL + "/stack/"+stackId+"/tasks/"+taskId,
        method: 'GET'
    });
} 

export function signup(signupRequest) {
    return request({
        url: config.API_BASE_URL + "/auth/signup",
        method: 'POST',
        body: JSON.stringify(signupRequest)
    });
}

export function base64toBlob(base64Data, contentType) {
    contentType = contentType || '';
    var sliceSize = 1024;
    var byteCharacters = atob(base64Data);
    var bytesLength = byteCharacters.length;
    var slicesCount = Math.ceil(bytesLength / sliceSize);
    var byteArrays = new Array(slicesCount);

    for (var sliceIndex = 0; sliceIndex < slicesCount; ++sliceIndex) {
        var begin = sliceIndex * sliceSize;
        var end = Math.min(begin + sliceSize, bytesLength);

        var bytes = new Array(end - begin);
        for (var offset = begin, i = 0; offset < end; ++i, ++offset) {
           bytes[i] = byteCharacters[offset].charCodeAt(0);
        }
        byteArrays[sliceIndex] = new Uint8Array(bytes);
    }
    return new Blob(byteArrays, { type: contentType });
}