## Calculator GUI

In this project, you will build on your prefix notation calculator TUI.

You will add the following new features (more details on each below):

* You will support _name bindings_. Essentially, variable names that map to values. These variables should be mutable, i.e., their values can be changed.
* You will allow the user to write expressions in _postfix_ or _prefix_ notation, as they prefer.
* You will use JavaFX to build a graphical user interface for the calculator.

## Learning objectives

This project directly addresses the following course learning objectives:

* Use Application Frameworks 
* Use functional programming and write lambda expressions
* Develop and properly organise multi-source file projects
* Use modularity in building a project
* Describe single inheritance, using abstract base classes
* Describe class members and methods
* Use polymorphic methods and interface types
* Implement and evaluate for use a selection of object-oriented design patterns

## Starter code 

Obtain the starter code using this GitHub link: **LINK IN CANVAS**

## Name bindings (variables)

Previously used the composite design pattern to support a hierarchy of expression types, including binary expressions and constants (numbers).

You will add support to this hierarchy for _name bindings_. That is, a user should be able to evaluate an expression like:

```
% 7 a
```

...assuming that `a` is previous defined.

This should tell you that you now need to keep track of an _environment_ that can map names to values. This can be as simple as a `HashMap<String, double>`.

So for the expression `% 7 a`, you will look up the name `a` in your environment and substitute in the appropriate value in the resulting modulo expression.

There are two possible ways in which you can do this:

1. You conduct your lookups while _parsing_ the expression into an expression tree. If you do things this way, you will not need to create a new type of Expression (e.g., `NameExpression`), because by the time things reach the expression tree, the name `a` will have already been resolved into some value. OR,
2. You create a new type of Expression whose `evaluate` method looks up its name in a shared environment and returns the value it receives (or throws an exception because of the unknown name).

Presumably your History is storing Expression objects.

If you go with option #1, you lose the ability to list expressions in history with the variable names they used. That is, if `a` was given the value `3`, the expression `% 7 a` would get saved in your history as `% 7 3`, masking the fact that `3` was bound to the variable `a`.

If you go with option #2, the look-up is performed during evaluation, so the expression is saved as `% 7 a`. In terms of time complexity this should not be too onerous if you use a data-structure with fast direct lookups for your environment.

This has the added benefit that users can _edit_ values associated with names if they so choose. And new computations using those variables will reflect their new values.

Therefore, I will require that you go with option #2. 

## Postfix notation + Strategy design pattern

In Project 1, you implemented a prefix notation parser that could read expressions written like

```
> + 3 - 10 7
```

For this project, you will allow the user to choose whether they want to write expressions using prefix or _postfix_ notation, and to freely switch between them from one calculation to the next.

This is an implementation of the _Strategy design pattern_. Other modules in your project should not know or care what type of Parser is being used. You can use a `Parser` interface with a `parse` abstract method, and two implementing subclasses `Prefix` and `Postfix` for this.

Or you can simply use a `Parser` class with a `parseStrategy` function.

```java
private Function<String, Expression> parseStrategy;

// parseStrategy gets set to the appropriate function based on the user's choice
```

### Postfix notation

Postfix notation is simple: instead of the operator coming before the operands, it comes after the operands.

```
> 3 10 7 - +
```

So the above operation would be evaluated as follows:

```
> 3 10 7 - +
> 3 3 +      // 10 7 - evaluates to 3
> 6
```

Roughly speaking, you can parse postfix expressions with the following algorithm:

```
Recurse once for the first operand
Recurse again for the second operand
Read an operator and create an expression
```

This can be somewhat easily achieved using a stack. Run through the written expression, placing each "value" (number or name) on a stack, and when you see an operator, pop the previous two things off the stack and create a binary expression. Place that binary expression back on the stack, since it will likely be used as an operand in some larger expression.

Your "stack" in the above steps can be the recursive call stack, or you can use a actual Stack data structure.

### Implications for the History feature

The way the history feature will be implemented in this GUI is slightly different than the TUI.
When the user selects an expression from history, the GUI will be populated with that expression. This way, the user has the ability to modify the expression before evaluation if needed.
Then, when the user presses the `=` button (see below), the expression will be calculated.

At this point you should ask yourself what should happen in the following scenario:

* User evaluates some expression, say `+ 3 ^ 5 2` using prefix notation
* User changes their mode to postfix notation
* User selects that previous expression `+ 3 ^ 5 2` from history

Should the expression be written into the text field as prefix notation? If you do this, the evaluate functionality is still expected it to be written in postfix, so things will break.

You could "remember", for each expression, the notation in which it was originally written, but that seems like couples together parsing and evaluation too much.

In reality, if you have your Expression Tree well set up, you should be able to "reproduce" a written expression using whatever notation is currently selected. So your GUI's history should display expressions using the notation that the user currently has selected, no matter what format they were originally written using. And the selected expressions from history should be populated into the text field using the appropriate notation.

If the user switches notations halfway while typing an expression, that's on them. It would be difficult to change it up on-the-fly, because at that point you may not have a complete expression to convert.

## The GUI

You will create your GUI using JavaFX. If you want to use some other Java graphics platform, talk to me first.

I'll leave the GUI design up to you. But it must have the following features:

* A grid of buttons for the digits 0–9.
* Buttons for the six arithmetic operators.
* A `=` button that, when clicked, evaluates the expression the user constructs.
* A text field (`TextField`) that shows the expression being constructed by the user, and where the solution will be displayed when the user clicks `=`.
* A drop-down menu (`ComboBox`) that shows the user the history of expressions, where the most recently evaluated expressions are on top.
* A pair of radio buttons that let the user indicate whether their expression is being constructed using prefix or postfix notation.
* A component showing the user the list of available name bindings (for example, `a = 10`, `b = 13.2`). You can do this as a drop-down menu, or you can have a separate panel (consider `TableView`) that shows the current "environment", i.e., the available name bindings. Selecting a binding "inserts" it into the text field where expressions are constructed.
* Facilities to add new name bindings to the environment. You can do this by having two text fields always be visible in the GUI (one for name, one for value), or you can have the user click something like "New Binding" which brings up an additional window (a new `Stage`) in which they can enter the required information.
* Facilities to edit values for name bindings.
* Appropriate error messaging. E.g., if the user types an invalid expression or an expression that uses undefined name bindings, you should give the user that feedback without crashing the program. If the user types a valid expression that overflows, you should tell the user that.

**Optional features**

* You can use CSS styling to apply some style rules to your calculator. See [this page](https://openjfx.io/javadoc/11/javafx.graphics/javafx/scene/doc-files/cssref.html) for more information.
* In addition to using the mouse to create expressions (by clicking on numbers and operators), you can allow the user to type in their expression using the keyboard and to evaluate their expression by hitting `Enter`.
* If the user switches between prefix and postfix halfway while constructing an expression, and if what they've written so far forms a valid expression, you can attempt to rewrite it in the newly chosen notation.

## Design considerations

I've sprinkled in various design choices in the write-up above. But many of the same constraints from Project 1 apply here.

**IDE warnings.** There should be no SonarLint or IDE warnings in your final submission (and no warnings should be
suppressed).

**Documentation.** All public methods and fields should be given a meaningful Javadoc comment explaining its purpose.
For methods, the @return, @param, and @throws tags should be used appropriately.

**Checking method inputs.** For all public methods or constructors that take input parameters, the validity of the
input parameters should be checked. If the input is invalid, throw an `IllegalArgumentException` with an appropriate
error message.

**Testing.** Your code should be tested. All non-GUI aspects of your code-base should achieve 100% branch coverage.
This should not be too onerous, since you are re-using your models and tests from Project 1. But you do need to add tests for the new logic that is being asked of you (namely, the parsing strategies and name bindings).
Your test suite is subject to the same software design guidelines as the rest of your code (except you don't have to write Javadocs for test methods).

**Module design.** By "module" here I mean "any named subroutine or collection of subroutines". This includes packages,
classes, and methods. I will assess your submission by manually reading through the code-base. I will look for evidence
of loosely coupled, cohesive modules, as well as for attentiveness to the _Effective Java_ items we talk about in class.

**Design patterns**. Your composite design from project one remains. Additionally, you will be using something akin to the [Strategy Design Pattern](https://refactoring.guru/design-patterns/strategy) to support switching between different parsing strategies. Finally, you may find the [Observer Pattern](https://refactoring.guru/design-patterns/observer) useful. We'll talk about this in class.
duct 2 points off the top if there are any warnings from SonarLint or from the IDE in general. (Exceptions to this rule include things that have come up in the Slack regarding switch statements.)
