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
        return <div>
                           <GridList cellHeight={'auto'} className={classes
                           .gridList} cols={1}>
                               <GridListTile key='stackHome1' cols={1}>
                                       <img src="/stackHome1M.jpg" />
                               </GridListTile>
                               <GridListTile key='stackHome2' cols={1}>
                                       <img src="/stackHome2M.jpg" height="600"
                                             width="800"/>
                               </GridListTile>
                           </GridList>
                       </div>
    }

    render() {
        return (
           <this.StackHome/>
        )
    }
}

export default Home;