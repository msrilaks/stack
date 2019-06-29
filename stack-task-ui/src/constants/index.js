const dev = {
  ACCESS_TOKEN: 'accessToken',
    API_BASE_URL: 'http://localhost:8080',
    OAUTH2_REDIRECT_URI: 'http://localhost:3000/oauth2/redirect',
    GOOGLE_AUTH_URL: 'http://localhost:8080' + '/oauth2/authorize/google?redirect_uri=' + 'http://localhost:3000/oauth2/redirect',
    FACEBOOK_AUTH_URL: 'http://localhost:8080' + '/oauth2/authorize/facebook?redirect_uri=' + 'http://localhost:3000/oauth2/redirect',
    GITHUB_AUTH_URL: 'http://localhost:8080' + '/oauth2/authorize/github?redirect_uri=' + 'http://localhost:3000/oauth2/redirect'
  };

  const prod = {
    ACCESS_TOKEN: 'accessToken',
    API_BASE_URL: 'http://www.stackitdown.com:8080',
    OAUTH2_REDIRECT_URI: 'http://www.stackitdown.com/oauth2/redirect',
    GOOGLE_AUTH_URL: 'http://www.stackitdown.com:8080' + '/oauth2/authorize/google?redirect_uri=' + 'http://www.stackitdown.com/oauth2/redirect',
    FACEBOOK_AUTH_URL: 'http://www.stackitdown.com:8080' + '/oauth2/authorize/facebook?redirect_uri=' + 'http://www.stackitdown.com/oauth2/redirect',
    GITHUB_AUTH_URL: 'http://www.stackitdown.com:8080' + '/oauth2/authorize/github?redirect_uri=' + 'http://www.stackitdown.com/oauth2/redirect'

  };

  export const config = process.env.REACT_APP_STAGE === 'development'
  ? dev
  : prod;
