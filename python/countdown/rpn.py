def pop_two(work):
    popped1 = work.pop()
    popped2 = work.pop()
    return (popped2, popped1)

def count_num_op_diff(arr):
    count = 0
    for item in arr:
        if isinstance(item, int):
            count += 1
        elif item != None:
            count -= 1
    return count

def rpn(arr, end_pos = 0):
    work = []
    if end_pos <= 0:
        end_pos = len(arr)

    for pos in range(end_pos):
        item = arr[pos]
        if isinstance(item, int):
            work.append(int(item))
        elif len(work) > 1:
            first_value, second_value = pop_two(work)
            next_value = 0
            if item == '+':
                next_value = first_value + second_value
            elif item == '-':
                next_value = first_value - second_value
                if next_value < 0:
                    return (False, None)
            elif item == '*':
                next_value = first_value * second_value
            elif item == '/':
                if second_value == 0:
                    return (False, None)
                elif first_value % second_value == 0:
                    next_value = first_value // second_value
                else:
                    return (False, None)

            work.append(next_value)
        else:
            return (False, None)


    return (True, work.pop())
