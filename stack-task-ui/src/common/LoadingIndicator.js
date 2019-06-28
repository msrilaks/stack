import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import CircularProgress from '@material-ui/core/CircularProgress';

const useStyles = makeStyles(theme => ({
    progress: {
      margin: theme.spacing(2),
    },
  }));
export default function LoadingIndicator(props) {
    const classes = useStyles();
    return (
        <CircularProgress className={classes.progress} />
        // <div className="loading-indicator" style={{display: 'block', textAlign: 'center', marginTop: '30px'}}>
        //     Loading ...
        // </div>
    );
}