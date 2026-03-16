mod rpn_element;

use std::env;
use rpn_element::RPNElement;

fn perform_countdown_rpn(args: &[RPNElement]) -> Result<i32, String> {
    let mut stack: Vec<i32> = vec![];
    for element in args.iter() {
        if element.is_operator() && stack.len() < 2 {
            return Err(String::from("Not enough elements"));
        }

        match element {
            RPNElement::Add => {
                let result = stack.pop().unwrap() + stack.pop().unwrap();
                stack.push(result);
            }
            RPNElement::Subtract => {
                let second_value = stack.pop().unwrap();
                let result = stack.pop().unwrap() - second_value;
                if result < 0 {
                    return Err(String::from("Countdown does not allow negatives"));
                }
                stack.push(result);
            }
            RPNElement::Multiply => {
                let result = stack.pop().unwrap() * stack.pop().unwrap();
                stack.push(result);
            }
            RPNElement::Divide => {
                let second_value = stack.pop().unwrap();
                let first_value = stack.pop().unwrap();
                if second_value == 0 {
                    return Err(String::from("Division by zero"));
                }
                if first_value % second_value != 0 {
                    return Err(String::from("Fractional values not allowed"));
                }
                let result = first_value / second_value;
                stack.push(result);
            }
            RPNElement::Number(val) => {
                stack.push(*val);
            }
        }
    }

    Ok(stack.pop().unwrap())
}

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

#[derive(Debug, Clone)]
struct Block {
    symbol: String,
    infix: String
}

fn can_match(val1: &String, val2: &String) -> bool {
    !(val1 == "+" || val1 == "-" || val1 == "*" || val1 == "/") ||
    ((val1 == "+" || val1 == "-") && (val2 == "+" || val2 == "-"))
}

fn result_to_infix(elements: &[RPNElement]) -> String {
    let mut blocks: Vec<Block> = vec![];
    for element in elements.iter() {
        if element.is_operator() {
            let second = blocks.pop().unwrap();
            let first = blocks.pop().unwrap();
            let mut first_string = first.infix;
            if !can_match(&first.symbol, &element.to_string()) {
                first_string = format!("({})", first_string);
            }
            let mut second_string = second.infix;
            if !can_match(&second.symbol, &element.to_string()) {
                second_string = format!("({})", second_string);
            }
            let block = Block {
                symbol: element.to_string(),
                infix: format!("{} {} {}", first_string, element.to_string(), second_string)
            };
            blocks.push(block);
        } else {
            let block = Block {
                symbol: element.to_string(),
                infix: element.to_string()
            };
            blocks.push(block);
        }
    }

    blocks.pop().unwrap().infix
}

fn recurse(
    elements: &mut Vec<RPNElement>,
    remainder: &Vec<i32>,
    next: usize,
    diff: usize,
    target: i32,
) -> Option<String> {
    if diff == 1 {
        match perform_countdown_rpn(&elements[0..next]) {
            Ok(result) => {
                if result == target {
                    return Some(result_to_infix(&elements[0..next]));
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
