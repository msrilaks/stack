import React, { Component } from 'react';
import './Profile.css';
import Popover from '@material-ui/core/Popover';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Avatar from '@material-ui/core/Avatar';
import { grey } from '@material-ui/core/colors';
import PersonIcon from '@material-ui/icons/Person';

const useStyles = makeStyles(theme => ({
  popover: {
    pointerEvents: 'none',
  },
  avatar: {
      backgroundColor: grey[500],
    },
  paper: {
    padding: theme.spacing(1),
  },
}));

class Profile extends Component {
    constructor(props) {
        super(props);
        console.log(props);
        this.MouseOverPopover=this.MouseOverPopover.bind(this);
    }

    MouseOverPopover() {
        const classes = useStyles();
        const [anchorEl, setAnchorEl] = React.useState(null);

        const handlePopoverOpen = event => {
            setAnchorEl(event.currentTarget);
        };

        const handlePopoverClose = () => {
            setAnchorEl(null);
        };

        const open = Boolean(anchorEl);
        let StackUserAvatar;
        if(this.props.imageUrl == '') {
            StackUserAvatar = <Avatar className={classes.avatar}>
                <PersonIcon/>
            </Avatar>
        } else {
            StackUserAvatar = <Avatar aria-label="task"
                className={classes.avatar}
                src={this.props.imageUrl}
                alt={this.props.name}>
            </Avatar>
        }
        return (
        <div>
            <Typography
                aria-owns={open ? 'mouse-over-popover' : undefined}
                aria-haspopup="true"
                onMouseEnter={handlePopoverOpen}
                onMouseLeave={handlePopoverClose}
            >
            {StackUserAvatar}
            </Typography>
            <Popover
                id="mouse-over-popover"
                className={classes.popover}
                classes={{
                paper: classes.paper,
                }}
                open={open}
                anchorEl={anchorEl}
                anchorOrigin={{
                vertical: 'bottom',
                horizontal: 'left',
                }}
                transformOrigin={{
                vertical: 'top',
                horizontal: 'left',
                }}
                onClose={handlePopoverClose}
                disableRestoreFocus
            >
                <div>
                    <Typography color="textPrimary" component="p"
                        variant="caption"
                        gutterBottom>
                            <span>{this.props.name}</span>
                    </Typography>
                    <Typography color="textPrimary"
                        variant="caption"
                        component="p">
                            <span>{this.props.email}</span>
                    </Typography>
                </div>
          </Popover>
        </div>
      );
    }

    render() {
        return (
           <this.MouseOverPopover/>
        );
    }
}

export default Profile