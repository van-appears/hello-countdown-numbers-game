use super::rpn_element::RPNElement;

struct Block {
    symbol: String,
    infix: String
}

fn can_match(val1: &String, val2: &String) -> bool {
    !(val1 == "+" || val1 == "-" || val1 == "*" || val1 == "/") ||
    ((val1 == "+" || val1 == "-") && (val2 == "+" || val2 == "-"))
}

pub fn rpn_to_infix(elements: &[RPNElement]) -> String {
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

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn single_element() {
        let elements = vec![
            RPNElement::Number(12)
        ];
        assert_eq!(rpn_to_infix(&elements), "12");
    }

    #[test]
    fn no_match() {
        let elements = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Add,
        ];
        assert_eq!(rpn_to_infix(&elements), "12 + 34");
    }

    #[test]
    fn left_match() {
        let elements = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Add,
            RPNElement::Number(56),
            RPNElement::Multiply
        ];
        assert_eq!(rpn_to_infix(&elements), "(12 + 34) * 56");
    }

    #[test]
    fn right_match() {
        let elements = vec![
            RPNElement::Number(78),
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Add,
            RPNElement::Multiply
        ];
        assert_eq!(rpn_to_infix(&elements), "78 * (12 + 34)");
    }

    #[test]
    fn both_match() {
        let elements = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Add,
            RPNElement::Number(56),
            RPNElement::Number(78),
            RPNElement::Add,
            RPNElement::Multiply
        ];
        assert_eq!(rpn_to_infix(&elements), "(12 + 34) * (56 + 78)");
    }

    #[test]
    fn plus_minus_match() {
        let elements = vec![
            RPNElement::Number(12),
            RPNElement::Number(34),
            RPNElement::Number(56),
            RPNElement::Number(78),
            RPNElement::Number(90),
            RPNElement::Add,
            RPNElement::Add,
            RPNElement::Subtract,
            RPNElement::Multiply
        ];

        assert_eq!(rpn_to_infix(&elements), "12 * (34 - 56 + 78 + 90)");
    }
}
