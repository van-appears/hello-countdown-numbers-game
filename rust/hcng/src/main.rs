mod rpn_countdown_calculator;
mod rpn_element;
mod rpn_to_infix;

use std::env;
use rpn_countdown_calculator::calculate;
use rpn_element::RPNElement;
use rpn_to_infix::rpn_to_infix;

fn parse_arguments() -> Result<Vec<i32>, String> {
    let args: Vec<String> = env::args().collect();
    let parse_failures = args
        .iter()
        .skip(1)
        .map(|str| str.parse::<i32>().is_err())
        .any(|err| err);

    if parse_failures {
        Err(String::from("Arguments are not all numbers"))
    } else {
        let numbers: Vec<i32> = args
            .iter()
            .skip(1)
            .map(|str| str.parse::<i32>().unwrap())
            .collect();
        if numbers.len() < 2 {
            Err(String::from("At least two numbers are needed"))
        } else {
            Ok(numbers)
        }
    }
}

fn recurse(
    elements: &mut Vec<RPNElement>,
    remainder: &Vec<i32>,
    next: usize,
    diff: usize,
    target: i32,
) -> Option<String> {
    if diff == 1 {
        match calculate(&elements[0..next]) {
            Ok(result) => {
                if result == target {
                    return Some(rpn_to_infix(&elements[0..next]));
                }
            }
            Err(_) => {
                return None;
            }
        }
    }

    if diff > 1 {
        let operators: Vec<RPNElement> = vec![
            RPNElement::Add,
            RPNElement::Subtract,
            RPNElement::Multiply,
            RPNElement::Divide,
        ];
        for operator in operators.into_iter() {
            let subset = remainder.clone();
            elements[next] = operator;

            let result = recurse(elements, &subset, next + 1, diff - 1, target);
            if result.is_some() {
                return result;
            }
        }
    }

    for idx in 0..remainder.len() {
        let mut subset = remainder.clone();
        let piece = subset.remove(idx);
        elements[next] = RPNElement::Number(piece);

        let result = recurse(elements, &subset, next + 1, diff + 1, target);
        if result.is_some() {
            return result;
        }
    }

    None
}

fn solve(numbers: Vec<i32>, target: i32) {
    let mut elements = vec![RPNElement::Number(0); numbers.len() * 2 - 1];
    match recurse(&mut elements, &numbers, 0, 0, target) {
        Some(result) => {
            println!("{}", result);
        },
        None => {
            println!("No solution found");
        }
    }
}

fn main() {
    match parse_arguments() {
        Ok(mut numbers) => {
            let target = numbers.pop();
            solve(numbers, target.unwrap());
        }
        Err(message) => {
            println!("{}", message);
        }
    }
}
