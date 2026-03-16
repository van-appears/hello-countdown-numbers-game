#[derive(Debug, Clone)]
pub enum RPNElement {
    Add,
    Subtract,
    Multiply,
    Divide,
    Number(i32),
}

impl RPNElement {
    pub fn is_operator(&self) -> bool {
        match *self {
            RPNElement::Number(_) => false,
            _ => true,
        }
    }
    pub fn to_string(&self) -> String {
        match *self {
            RPNElement::Add => String::from("+"),
            RPNElement::Subtract => String::from("-"),
            RPNElement::Multiply => String::from("*"),
            RPNElement::Divide => String::from("/"),
            RPNElement::Number(x) => x.to_string()
        }
    }
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn is_operator_number() {
        let element = RPNElement::Number(1);
        assert_eq!(element.is_operator(), false);
    }

    #[test]
    fn is_operator_add() {
        let element = RPNElement::Add;
        assert_eq!(element.is_operator(), true);
    }

        #[test]
    fn is_operator_subtract() {
        let element = RPNElement::Subtract;
        assert_eq!(element.is_operator(), true);
    }

        #[test]
    fn is_operator_multiply() {
        let element = RPNElement::Multiply;
        assert_eq!(element.is_operator(), true);
    }

        #[test]
    fn is_operator_divide() {
        let element = RPNElement::Divide;
        assert_eq!(element.is_operator(), true);
    }

    #[test]
    fn to_string_number() {
        let element = RPNElement::Number(123);
        assert_eq!(element.to_string(), "123");
    }

    #[test]
    fn to_string_add() {
        let element = RPNElement::Add;
        assert_eq!(element.to_string(), "+");
    }

        #[test]
    fn to_string_subtract() {
        let element = RPNElement::Subtract;
        assert_eq!(element.to_string(), "-");
    }

        #[test]
    fn to_string_multiply() {
        let element = RPNElement::Multiply;
        assert_eq!(element.to_string(), "*");
    }

        #[test]
    fn to_string_divide() {
        let element = RPNElement::Divide;
        assert_eq!(element.to_string(), "/");
    }
}