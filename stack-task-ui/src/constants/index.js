// export const API_BASE_URL = 'http://localhost:8080';

const API_BASE_URL_INT = process.env.REACT_APP_STAGE  === 'development' ?
'http://localhost:8080' : 'http://35.233.233.97:8080';
export const API_BASE_URL=API_BASE_URL_INT;

//export const OAUTH2_REDIRECT_URI = 'http://localhost:3000/oauth2/redirect'
const OAUTH2_REDIRECT_URI_INT = process.env.REACT_APP_STAGE  === 'development' ?
'http://localhost:3000/oauth2/redirect' : 'http://stackitdown.com/oauth2/redirect';
export const OAUTH2_REDIRECT_URI=OAUTH2_REDIRECT_URI_INT;

export const ACCESS_TOKEN = 'accessToken';
export const GOOGLE_AUTH_URL = API_BASE_URL + '/oauth2/authorize/google?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const FACEBOOK_AUTH_URL = API_BASE_URL + '/oauth2/authorize/facebook?redirect_uri=' + OAUTH2_REDIRECT_URI;
export const GITHUB_AUTH_URL = API_BASE_URL + '/oauth2/authorize/github?redirect_uri=' + OAUTH2_REDIRECT_URI;



