<%@ page import="de.aljoshavieth.kartenverkauf.Kartenverkauf" %>
<%@ page import="java.util.StringJoiner" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: Aljosha
  Date: 04.12.2020
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="de">

<head>
    <meta charset="UTF-8">
    <meta name="author" content="Aljosha Vieth">
    <title>Aljoshas Ticketverkauf</title>
    <link rel="stylesheet" href="style.css">

    <style>
        <%!
        private String getStyleString(ArrayList<Integer> list){
            StringJoiner stringJoiner = new StringJoiner(", #n");
            stringJoiner.add(" ");
            for(int i = 0; i<list.size(); i++){
                Integer index = list.get(i);
                index++;
                stringJoiner.add(index.toString());
            }
            String style = stringJoiner.toString();
            if(style.length() >=3){
                style = style.substring(2);
            }
            return style;
        }
        %>
        <%
        //TODO change with singleton
        Kartenverkauf kartenverkauf = new Kartenverkauf();
        String available = getStyleString(kartenverkauf.getAllAvailableTickets());
        String reserved = getStyleString(kartenverkauf.getAllReservedTickets());
        String sold = getStyleString(kartenverkauf.getAllSoldTickets());
        System.out.println("Index reached");
        //TODO get all tickets and display
        %>

        <%= available %>
        {
            background-color: #2ecc71
        ;
        }

        <%= reserved %>
        {
            background-color: #e74c3c
        ;
        }

        <%= sold %>
        {
            background-color: #9b59b6
        ;
        }

    </style>
</head>


<body>
<h1>Ticketverkauf</h1>
<table>
    <tr>
        <td id="n1">1</td>
        <td id="n2">2</td>
        <td id="n3">3</td>
        <td id="n4">4</td>
        <td id="n5">5</td>
        <td id="n6">6</td>
        <td id="n7">7</td>
        <td id="n8">8</td>
        <td id="n9">9</td>
        <td id="n10">10</td>
    </tr>
    <tr>
        <td id="n11">11</td>
        <td id="n12">12</td>
        <td id="n13">13</td>
        <td id="n14">14</td>
        <td id="n15">15</td>
        <td id="n16">16</td>
        <td id="n17">17</td>
        <td id="n18">18</td>
        <td id="n19">19</td>
        <td id="n20">20</td>
    </tr>
    <tr>
        <td id="n21">21</td>
        <td id="n22">22</td>
        <td id="n23">23</td>
        <td id="n24">24</td>
        <td id="n25">25</td>
        <td id="n26">26</td>
        <td id="n27">27</td>
        <td id="n28">28</td>
        <td id="n29">29</td>
        <td id="n30">30</td>
    </tr>
    <tr>
        <td id="n31">31</td>
        <td id="n32">32</td>
        <td id="n33">33</td>
        <td id="n34">34</td>
        <td id="n35">35</td>
        <td id="n36">36</td>
        <td id="n37">37</td>
        <td id="n38">38</td>
        <td id="n39">39</td>
        <td id="n40">40</td>
    </tr>
    <tr>
        <td id="n41">41</td>
        <td id="n42">42</td>
        <td id="n43">43</td>
        <td id="n44">44</td>
        <td id="n45">45</td>
        <td id="n46">46</td>
        <td id="n47">47</td>
        <td id="n48">48</td>
        <td id="n49">49</td>
        <td id="n50">50</td>
    </tr>
    <tr>
        <td id="n51">51</td>
        <td id="n52">52</td>
        <td id="n53">53</td>
        <td id="n54">54</td>
        <td id="n55">55</td>
        <td id="n56">56</td>
        <td id="n57">57</td>
        <td id="n58">58</td>
        <td id="n59">59</td>
        <td id="n60">60</td>
    </tr>
    <tr>
        <td id="n61">61</td>
        <td id="n62">62</td>
        <td id="n63">63</td>
        <td id="n64">64</td>
        <td id="n65">65</td>
        <td id="n66">66</td>
        <td id="n67">67</td>
        <td id="n68">68</td>
        <td id="n69">69</td>
        <td id="n70">70</td>
    </tr>
    <tr>
        <td id="n71">71</td>
        <td id="n72">72</td>
        <td id="n73">73</td>
        <td id="n74">74</td>
        <td id="n75">75</td>
        <td id="n76">76</td>
        <td id="n77">77</td>
        <td id="n78">78</td>
        <td id="n79">79</td>
        <td id="n80">80</td>
    </tr>
    <tr>
        <td id="n81">81</td>
        <td id="n82">82</td>
        <td id="n83">83</td>
        <td id="n84">84</td>
        <td id="n85">85</td>
        <td id="n86">86</td>
        <td id="n87">87</td>
        <td id="n88">88</td>
        <td id="n89">89</td>
        <td id="n90">90</td>
    </tr>
    <tr>
        <td id="n91">91</td>
        <td id="n92">92</td>
        <td id="n93">93</td>
        <td id="n94">94</td>
        <td id="n95">95</td>
        <td id="n96">96</td>
        <td id="n97">97</td>
        <td id="n98">98</td>
        <td id="n99">99</td>
        <td id="n100">100</td>
    </tr>

</table>
<br/>
<table>
    <tr>
        <td id="free">verfügbar</td>
        <td id="reserved">reserviert</td>
        <td id="sold">verkauft</td>
    </tr>
</table>
<br/>
<table>
    <tr>
        <td><a href="sell_ticket.html">Verkauf eines Freien Tickets</a></td>
        <td><a href="reserve_ticket.html">Reservierung eines Tickets</a></td>
        <td><a href="sell_reserved_ticket.html">Verkauf eines reservierten Tickets</a></td>
        <td><a href="cancel_ticket.html">Stornierung eines Tickets</a></td>
        <td><a href="cancel_all_reservations.html">Reservierung aufheben</a></td>
    </tr>
</table>

</body>

</html>