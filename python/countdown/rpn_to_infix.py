def rpn_to_infix(rpn_stack):
    infix_stack = []
    for item in rpn_stack:
        if isinstance(item, int):
            infix_stack.append(f"{item}")
        else:
            val1 = infix_stack.pop()
            val2 = infix_stack.pop()
            infix_stack.append(f"({val2} {item} {val1})")

    return infix_stack.pop()
