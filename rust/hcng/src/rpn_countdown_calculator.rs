use super::rpn_element::RPNElement;

pub fn calculate(elements: &[RPNElement]) -> Result<i32, String> {
    if elements.len() == 0 {
        return Err(String::from("No elements provided"));
    }

    let mut stack: Vec<i32> = vec![];
    for element in elements.iter() {
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
                    return Err(String::from("Countdown does not allow fractions"));
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

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn no_elements_is_err() {
        let elements: Vec<RPNElement> = vec![];
        assert_eq!(calculate(&elements).unwrap_err(), "No elements provided");
    }

    #[test]
    fn number_elements() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12)
        ];
        assert_eq!(calculate(&elements).unwrap(), 12);
    }

    #[test]
    fn not_enough_numbers_for_operator_is_err() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Add
        ];
        assert_eq!(calculate(&elements).unwrap_err(), "Not enough elements");
    }

    #[test]
    fn add() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Add
        ];
        assert_eq!(calculate(&elements).unwrap(), 46);
    }

    #[test]
    fn multiply() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Multiply
        ];
        assert_eq!(calculate(&elements).unwrap(), 408);
    }

    #[test]
    fn subtract_positive() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(34),
            RPNElement::Number(12),
            RPNElement::Subtract
        ];
        assert_eq!(calculate(&elements).unwrap(), 22);
    }

    #[test]
    fn subtract_negative_is_err() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Subtract
        ];
        assert_eq!(calculate(&elements).unwrap_err(), "Countdown does not allow negatives");
    }

    #[test]
    fn divide() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(24),
            RPNElement::Number(12),
            RPNElement::Divide
        ];
        assert_eq!(calculate(&elements).unwrap(), 2);
    }

    #[test]
    fn divide_fraction_is_err() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Number(24),
            RPNElement::Divide
        ];
        assert_eq!(calculate(&elements).unwrap_err(), "Countdown does not allow fractions");
    }

    #[test]
    fn divide_by_zero_is_err() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Number(0),
            RPNElement::Divide
        ];
        assert_eq!(calculate(&elements).unwrap_err(), "Division by zero");
    }

    #[test]
    fn results_pushed_to_stack() {
        let elements: Vec<RPNElement> = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Multiply,
            RPNElement::Number(56),
            RPNElement::Subtract
        ];
        assert_eq!(calculate(&elements).unwrap(), 352);
    }
}
