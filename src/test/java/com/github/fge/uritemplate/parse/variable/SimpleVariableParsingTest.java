/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.uritemplate.parse.variable;

import com.github.fge.uritemplate.ExceptionMessages;
import com.github.fge.uritemplate.URITemplateParseException;
import com.github.fge.uritemplate.parse.VariableSpecParser;
import com.github.fge.uritemplate.vars.VariableSpec;
import com.github.fge.uritemplate.vars.VariableSpecType;
import com.google.common.collect.Lists;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.nio.CharBuffer;
import java.util.Iterator;
import java.util.List;

import static org.testng.Assert.*;

public final class SimpleVariableParsingTest
{
    @DataProvider
    public Iterator<Object[]> validInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;

        input = "foo";
        list.add(new Object[]{ input });

        input = "%33foo";
        list.add(new Object[]{ input });

        input = "foo%20";
        list.add(new Object[]{ input });

        input = "foo_%20bar";
        list.add(new Object[]{ input });

        input = "FoOb%02ZAZE287";
        list.add(new Object[]{ input });

        input = "foo.bar";
        list.add(new Object[]{ input });

        input = "foo_%20bar.baz%af.r";
        list.add(new Object[]{ input });

        return list.iterator();
    }

    @Test(dataProvider = "validInputs")
    public void simpleVariablesAreParsedCorrectly(final String input)
        throws URITemplateParseException
    {
        final CharBuffer buffer = CharBuffer.wrap(input).asReadOnlyBuffer();
        final VariableSpec varspec = VariableSpecParser.parse(buffer);

        assertEquals(varspec.getName(), input);
        assertSame(varspec.getType(), VariableSpecType.SIMPLE,
            "unexpected class for parsed variable");
        assertFalse(buffer.hasRemaining());
    }

    @DataProvider
    public Iterator<Object[]> invalidInputs()
    {
        final List<Object[]> list = Lists.newArrayList();

        String input;
        String message;
        int offset;

        input = "";
        message = ExceptionMessages.EMPTY_NAME;
        offset = 0;
        list.add(new Object[]{input, message, offset});

        input = "%";
        message = ExceptionMessages.PERCENT_SHORT_READ;
        offset = 0;
        list.add(new Object[]{input, message, offset});

        input = "foo..bar";
        message = ExceptionMessages.EMPTY_NAME;
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = ".";
        message = ExceptionMessages.EMPTY_NAME;
        offset = 0;
        list.add(new Object[]{input, message, offset});

        input = "foo%ra";
        message = ExceptionMessages.ILLEGAL_PERCENT;
        offset = 4;
        list.add(new Object[]{input, message, offset});

        input = "foo%ar";
        message = ExceptionMessages.ILLEGAL_PERCENT;
        offset = 5;
        list.add(new Object[]{input, message, offset});

        return list.iterator();
    }

    @Test(dataProvider = "invalidInputs")
    public void invalidInputsThrowAppropriateExceptions(final String input,
        final String message, final int offset)
    {
        try {
            VariableSpecParser.parse(CharBuffer.wrap(input).asReadOnlyBuffer());
            fail("No exception thrown!!");
        } catch (URITemplateParseException e) {
            assertEquals(e.getOriginalMessage(), message);
            assertEquals(e.getOffset(), offset);
        }
    }
}
