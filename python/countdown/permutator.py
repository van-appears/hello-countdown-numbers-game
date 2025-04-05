from .rpn import rpn, count_num_op_diff

OPERATORS = ["+", "-", "*", "/"]

def extend_with_op(rpn_stack, remaining_nums, pos, target):
    stack_before = rpn_stack[:pos]
    if count_num_op_diff(stack_before) > 1:
        for op in OPERATORS:
            rpn_stack[pos] = op
            allowed, value = rpn(rpn_stack, pos + 1)
            if allowed and value == target:
                return rpn_stack[:pos + 1]

            result = extend(rpn_stack, remaining_nums, pos + 1, target)
            if result != None:
                return result
    return None

def extend_with_num(rpn_stack, remaining_nums, pos, target):
    for idx in range(len(remaining_nums)):
        rpn_stack[pos] = remaining_nums[idx]
        allowed, value = rpn(rpn_stack, pos + 1)
        if allowed and value == target:
            return rpn_stack[:pos + 1]

        next_remaining_nums = remaining_nums[:idx] + remaining_nums[idx + 1:]
        result = extend(rpn_stack, next_remaining_nums, pos + 1, target)
        if result != None:
            return result
    return None

def extend(rpn_stack, remaining_nums, pos, target):
    result = extend_with_op(rpn_stack, remaining_nums, pos, target)
    if result != None:
         return result

    result = extend_with_num(rpn_stack, remaining_nums, pos, target)
    if result != None:
         return result

def permutator(nums, total):
    rpn_stack = [None] * (len(nums) * 2 - 1)
    return extend(rpn_stack, nums, 0, total)
