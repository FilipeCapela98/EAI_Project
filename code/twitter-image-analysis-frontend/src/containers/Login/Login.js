import React from 'react';
import PropTypes from 'prop-types';
import { Redirect } from 'react-router-dom';
import { connect } from 'react-redux';
import {
  Paper,
  Typography,
  TextField,
  Button,
  withStyles,
} from '@material-ui/core';
import { login, isAuthenticated } from '../../modules/users';

const styles = theme => ({
  paper: {
    padding: theme.spacing.unit * 5,
  },
  textField: {
    marginTop: theme.spacing.unit * 5,
    marginBottom: theme.spacing.unit * 5,
  },
});

export class Login extends React.Component {
  static propTypes = {
    classes: PropTypes.object.isRequired,
    isAuthenticated: PropTypes.bool.isRequired,
    login: PropTypes.func.isRequired,
  };

  input = null;

  onSubmit = event => {
    event.preventDefault();
    const { value } = this.input;

    if (!value.trim()) {
      return;
    }
    this.props.login(value);
    this.input.value = '';
  };

  render() {
    const { classes, isAuthenticated } = this.props;

    if (isAuthenticated) {
      return <Redirect to="/" />;
    }

    return (
      <Paper className={classes.paper}>
        <Typography variant="h3" style={{textAlign:"center"}}>Login</Typography>
        <form onSubmit={this.onSubmit}>
          <TextField
            required
            fullWidth
            type="text"
            placeholder="Your username"
            className={classes.textField}
            style={{width:"100%"}}
            inputRef={ref => (this.input = ref)}
          />
          <div style={{textAlign:"center", width:"100%"}}>
          <Button variant="outlined" type="submit">
            Sign In
          </Button>
          </div>
        </form>
      </Paper>
    );
  }
}

const mapStateToProps = state => ({
  isAuthenticated: isAuthenticated(state.users),
});

const mapDispatchToProps = { login };

export default withStyles(styles)(
  connect(
    mapStateToProps,
    mapDispatchToProps,
  )(Login),
);
