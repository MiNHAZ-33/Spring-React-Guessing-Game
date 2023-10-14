import React, { useState } from "react";
import { Card, Form, Button } from "react-bootstrap";
import Loader from "./Loader";
import axios from "axios";

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
        axios.get(`http://localhost:8080/api/guess?userName=Minhaz&guess=1`)
            .then(res => {
                setLoading(false);
                console.log(res)
                if (res.data.status === "success") {
                    setMessage(res.data.message);
                    setExtraMessage(res.data.extra);
                } else if (res.data.status === "high") {
                    setMessage(res.data.message);
                    setExtraMessage(res.data.extra);
                }
                else {
                    setMessage(res.data.message);
                    setExtraMessage(res.data.extra);
                }
            }).catch(err => {
                console.log(err)
                setLoading(false);
            })
        setGuess("");
    }

    return (
        <div className="container mt-5">
            <Card>
                <Card.Body>
                    <Card.Title>Guess the Number</Card.Title>
                    <Form onSubmit={handleGuessSubmit}>
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
                    </Form>
                    <p className="mt-3">{message}</p>
                    <p>{extraMessage}</p>
                </Card.Body>
            </Card>
        </div>
    );
}

export default GamePage;