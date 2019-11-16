import React, { Component } from 'react';
import './Home.css';

class Home extends Component {
    constructor() {
        super();
        this.StackHome = this.StackHome.bind(this);
    }

    StackHome(){
        if(/iPhone|iPad|iPod|Android|webOS|BlackBerry|Windows Phone/i.test(navigator.userAgent)
            || window.innerWidth < 480){
                //code for mobile
                return <div class="fadein">
                    <img id="f2" src="/stackHome1S.jpg" />
                    <img id="f1" src="/stackHome2S.jpg"/>
                    <p id="f3" class="centered"></p>
                </div>}
        return <div class="fadein">
            <img id="f2" src="/stackHome1M.jpg" />
            <img id="f1" src="/stackHome2M.jpg"/>
            <p id="f3" class="centered"></p>
        </div>
    }

    render() {
        return (
           <this.StackHome/>
        )
    }
}

export default Home;