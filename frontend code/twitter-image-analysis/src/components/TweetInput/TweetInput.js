import React from 'react';
import PropTypes from 'prop-types';
import { Paper, TextField, Button, Grid, withStyles } from '@material-ui/core';

const styles = theme => ({
  paper: {
    padding: theme.spacing.unit * 2,
  },
  tweetButton: {
    marginTop: theme.spacing.unit,
  },
});

class TweetInput extends React.Component {

  constructor(props) {
    super(props);
    console.log(props)
  }
  static propTypes = {
    classes: PropTypes.object.isRequired,
    onSubmit: PropTypes.func,
  };

  static defaultProps = {
    onSubmit: () => {},
  };

  input = React.createRef();

  onSubmit = event => {
    event.preventDefault();
    const { value } = this.input.current;

    if (!value.trim()) {
      return;
    }
    this.props.onSubmit(value, event);
    this.input.current.value = '';
  };

  handleStop(event) {
    event.preventDefault();
    this.props.onStop(event);

  }

  render() {
    const { classes } = this.props;

    return (
      <Paper className={classes.paper}>
        <form onSubmit={this.onSubmit} autoComplete="off">
          <TextField
            required
            fullWidth
            multiline
            rows={2}
            placeholder="Enter Hashtags?"
            inputRef={this.input}
          />
          <Grid container justify="flex-end" spacing={24}>
            <Grid item>
            <Button
                variant="outlined"
                color="primary"
                type="submit"
                onClick= {this.handleStop.bind(this)}
                className={classes.tweetButton}
                value="Stop"
                mt={2}
              >
                Stop
              </Button>
            </Grid>
            <Grid item>
              <Button
                variant="outlined"
                color="primary"
                type="submit"
                className={classes.tweetButton}
                value="Send"
                mt={2}
              >
                Send
              </Button>
            </Grid>
          </Grid>
        </form>
      </Paper>
    );
  }
}

export default withStyles(styles)(TweetInput);
