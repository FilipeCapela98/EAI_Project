import React from 'react';
import PropTypes from 'prop-types';
import { connect } from 'react-redux';
import { Link } from 'react-router-dom';
import '../../App.css';
// import twitter_vision from '../../twitter_vision.png'
import twitter_vision from '../../Twitter_vision.svg';
import {
  AppBar,
  Toolbar,
  Typography,
  Button,
  withStyles,
  Avatar,
} from '@material-ui/core';
import { logout, getUserById, isAuthenticated } from '../../modules/users';
import colorFrom from '../../utils/colors';

const styles = theme => ({
  flex: {
    flexGrow: 1,
    marginLeft: theme.spacing.unit,
  },
  link: {
    textDecoration: 'none',
    color: 'inherit',
  },
});

export const Header = ({ classes, isAuthenticated, user, onLogout }) => (
  <AppBar>
    <Toolbar>
    <img src={twitter_vision} className="App-logo" alt="logo" style={{ paddingRight:'40px'}}/>
      {user && (
        <Avatar
          style={{
            backgroundColor: colorFrom(user.username)
          }}
        >
          {user.username[0]}
        </Avatar>
      )}
      <Typography variant="title" color="inherit" className={classes.flex}>
        <Link to="/" className={classes.link}>
          {user ? user.username : 'React Twitter'}
        </Link>
      </Typography>
      {isAuthenticated ? (
        <Button color="inherit" onClick={onLogout}>
          Logout
        </Button>
      ) : (
        <Button color="inherit" component={Link} to="/login">
          Login
        </Button>
      )}
    </Toolbar>
  </AppBar>
);

Header.propTypes = {
  classes: PropTypes.object.isRequired,
  isAuthenticated: PropTypes.bool.isRequired,
  user: PropTypes.shape({
    id: PropTypes.string,
    username: PropTypes.string.isRequired,
  }).isRequired,
  onLogout: PropTypes.func.isRequired,
};

const mapStateToProps = state => ({
  isAuthenticated: isAuthenticated(state.users),
  user: getUserById(state.users, state.users.active),
});

const mapDispatchToProps = {
  onLogout: logout,
};

// Probably better to use recompose
export default withStyles(styles)(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  )(Header),
);
