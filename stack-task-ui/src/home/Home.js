import React, { Component } from 'react';
import './Home.css';
import Container from '@material-ui/core/Container';
class Home extends Component {
    constructor() {
        super();
        this.StackHome = this.StackHome.bind(this);
    }

    StackHome(){
    return <React.Fragment>
          <Container maxWidth="sm">
                <img id="f2" src="/stackHome1M.jpg" />
                <img id="f1" src="/stackHome2M.jpg"/>
                <p id="f3" class="centered"></p>
          </Container>
        </React.Fragment>
    }

    render() {
        return (
           <this.StackHome/>
        )
    }
}

export default Home;