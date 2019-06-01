import React, {
    Component
} from "react";
import { GOOGLE_AUTH_URL, ACCESS_TOKEN } from '../constants';
export default class SocialLogin extends Component {
    render() {
        return (
            <div className="social-login">
                <a className="btn btn-block social-btn google" href={GOOGLE_AUTH_URL}>
                     Log in with Google</a>
            </div>
        );
    }
}
