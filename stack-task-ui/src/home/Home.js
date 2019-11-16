import React, { Component } from 'react';
import './Home.css';
import { makeStyles } from '@material-ui/core/styles';
import GridList from '@material-ui/core/GridList';
import GridListTile from '@material-ui/core/GridListTile';
import GridListTileBar from '@material-ui/core/GridListTileBar';

const useStyles = makeStyles(theme => ({
    gridList: {
        width: '100%',
        height: 'auto',
    },

}));
class Home extends Component {
    constructor() {
        super();
        this.StackHome = this.StackHome.bind(this);
    }

    StackHome(){
        const classes = useStyles();
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