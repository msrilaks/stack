import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import CircularProgress from '@material-ui/core/CircularProgress';
import LinearProgress from '@material-ui/core/LinearProgress';
const useStyles = makeStyles(theme => ({
    progress: {
      margin: theme.spacing(2),
    },
  }));
export default function LoadingIndicator(props) {
    const classes = useStyles();
    return (
        //
         <div className="loading-indicator" style={{display: 'block', textAlign:
          'center', marginTop: '100px'}}>
          <CircularProgress disableShrink />
         </div>
    );
}