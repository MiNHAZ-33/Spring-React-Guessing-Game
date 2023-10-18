import React, { useState } from "react";
import { Card, Form, Button } from "react-bootstrap";
import Loader from "./Loader";
import axios from "axios";
import { Link } from "react-router-dom";

function GamePage() {
    const [guess, setGuess] = useState("");
    const [message, setMessage] = useState("");
    const [extraMessage, setExtraMessage] = useState("");
    const [loading, setLoading] = useState(false);

    function handleGuessChange(event) {
        setGuess(event.target.value);
    }

    function handleGuessSubmit(event) {
        event.preventDefault();
        setLoading(true);
        let userName = localStorage.getItem("userName");
        axios.get(`http://localhost:8080/api/guess?userName=${userName}&guess=${guess}`)
            .then(res => {
                setLoading(false);
                console.log(res)
                if (res.data.status === "success") {
                    setMessage(res.data.message);
                    setExtraMessage(res.data.extra);
                } else if (res.data.status === "high") {
                    setMessage(res.data.message);
                    if (res.data.extra) {
                        setExtraMessage(res.data.extra);
                    }
                }
                else {
                    setMessage(res.data.message);
                    if (res.data.extra) {
                        setExtraMessage(res.data.extra);
                    }
                }
            }).catch(err => {
                console.log(err)
                setLoading(false);
            })
        setGuess("");
    }

    const playAgain = () => {
        let userName = localStorage.getItem("userName");
        axios.post(`http://localhost:8080/api/generate?userName=${userName}`)
            .then(res => {
                localStorage.setItem('userName', userName);
                console.log(res);
                setLoading(false);
                setMessage("");
                setExtraMessage("");
            }).catch(err => {
                setLoading(false)
            })
    }

    return (
        <div className="container mt-5">
            <Card>
                <Card.Body>
                    <Card.Title className="text-center">Guess a number between 1-10</Card.Title>
                    {
                        extraMessage === "" ? <Form onSubmit={handleGuessSubmit}>
                            <Form.Group>
                                <Form.Label>Guess:</Form.Label>
                                <Form.Control
                                    type="number"
                                    value={guess}
                                    onChange={handleGuessChange}
                                />
                            </Form.Group>
                            <div className="pt-4 text-center">
                                {
                                    loading ? <Loader /> : <Button variant="primary" type="submit">
                                        Submit
                                    </Button>
                                }
                            </div>
                        </Form> : <div className="text-center">
                            <h1>Game Over</h1>
                            <p>{message}</p>
                            <p>{extraMessage}</p>
                        </div>
                    }
                    <h2 className="mt-3 text-center">{message}</h2>
                    {extraMessage !== "" && <div className="text-center">
                        <button onClick={playAgain} className="btn btn-primary">Play Again</button>
                    </div>
                    }
                </Card.Body>
            </Card>
            <div>
                <Link to={"/history"}>
                    <h5 className="text-center text-white pt-5">Click here to show previous records</h5>
                </Link>
            </div>
        </div>
    );
}

export default GamePage;